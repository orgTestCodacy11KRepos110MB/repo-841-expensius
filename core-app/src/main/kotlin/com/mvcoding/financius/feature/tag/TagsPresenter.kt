/*
 * Copyright (C) 2015 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.financius.feature.tag

import com.mvcoding.financius.ModelState
import com.mvcoding.financius.feature.Presenter
import com.mvcoding.financius.feature.tag.Tag.Companion.noTag
import com.mvcoding.financius.feature.tag.TagsPresenter.BatchOperationMode.HIDDEN
import com.mvcoding.financius.feature.tag.TagsPresenter.BatchOperationMode.VISIBLE
import com.mvcoding.financius.feature.tag.TagsPresenter.DisplayType.MULTI_CHOICE
import com.mvcoding.financius.feature.tag.TagsPresenter.DisplayType.VIEW
import rx.Observable
import rx.Observable.merge

class TagsPresenter(
        private val tagsCache: TagsCache,
        private val displayType: TagsPresenter.DisplayType = VIEW,
        private var selectedTags: Set<Tag> = setOf()) : Presenter<TagsPresenter.View>() {
    private var batchOperationMode: BatchOperationMode = HIDDEN

    override fun onAttachView(view: View) {
        super.onAttachView(view)

        view.showBatchOperationMode(batchOperationMode)
        view.setDisplayType(displayType)
        if (displayType === MULTI_CHOICE || batchOperationMode == VISIBLE) {
            view.showSelectedTags(selectedTags.orEmpty())
        }

        unsubscribeOnDetach(view.onBatchOperationModeChanged().doOnNext { batchOperationMode = it }.subscribe { view.showBatchOperationMode(it) })
        unsubscribeOnDetach(tagsCache.tags().subscribe { view.showTags(it) })
        unsubscribeOnDetach(merge(view.onTagSelected(), view.onCreateTag().map { noTag }).subscribe { selectTag(view, it) })
        unsubscribeOnDetach(view.onSave().map { selectedTags }.subscribe { view.startResult(it) })
        unsubscribeOnDetach(view.onArchive().map { selectedTags }.doOnNext { archive(view, it) }.subscribe { selectedTags = setOf() })
    }

    private fun selectTag(view: View, tag: Tag) {
        when {
            displayType == VIEW && batchOperationMode == HIDDEN -> view.startTagEdit(tag)
            displayType == MULTI_CHOICE || batchOperationMode == VISIBLE ->
                selectedTags = if (selectedTags.contains(tag)) {
                    view.showTagSelected(tag, false)
                    selectedTags.minus(tag)
                } else {
                    view.showTagSelected(tag, true)
                    selectedTags.plus(tag)
                }
            else -> throw IllegalArgumentException("Display type $displayType is not supported.")
        }
    }

    private fun archive(view: View, tags: Set<Tag>) {
        view.remove(tags)
        view.showBatchOperationMode(HIDDEN)
        tagsCache.save(tags.map { it.withModelState(ModelState.ARCHIVED) }.toSet())
    }

    interface View : Presenter.View {
        fun onBatchOperationModeChanged(): Observable<BatchOperationMode>
        fun onTagSelected(): Observable<Tag>
        fun onCreateTag(): Observable<Unit>
        fun onSave(): Observable<Unit>
        fun onArchive(): Observable<Unit>
        fun showBatchOperationMode(batchOperationMode: BatchOperationMode)
        fun setDisplayType(displayType: DisplayType)
        fun showSelectedTags(selectedTags: Set<Tag>)
        fun showTagSelected(tag: Tag, selected: Boolean)
        fun showTags(tags: List<Tag>)
        fun remove(tags: Set<Tag>)
        fun startTagEdit(tag: Tag)
        fun startResult(tag: Set<Tag>)
    }

    enum class DisplayType {
        VIEW, MULTI_CHOICE
    }

    enum class BatchOperationMode {
        HIDDEN, VISIBLE
    }
}
