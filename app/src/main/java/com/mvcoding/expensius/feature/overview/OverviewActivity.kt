/*
 * Copyright (C) 2018 Mantas Varnagiris.
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

package com.mvcoding.expensius.feature.overview

import com.mvcoding.expensius.feature.BaseActivity

class OverviewActivity : BaseActivity()/*, OverviewPresenter.View */ {

//    companion object {
//        fun start(context: Context) = ActivityStarter(context, OverviewActivity::class).start()
//    }
//
//    private val presenter by lazy { provideOverviewPresenter() }
//    private val dateFormatter by lazy { provideDateFormatter() }
//    private val toolbarClicks by lazy { toolbar.itemClicks().share() }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_overview)
//        removeUpArrowFromToolbar()
//        presenter.attach(this)
//        tagsReportView.isEnabled = false
//    }
//
//    override fun onDestroy() {
//        presenter.detach(this)
//        super.onDestroy()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        super.onCreateOptionsMenu(menu)
//        menuInflater.inflate(R.menu.overview, menu)
//        return true
//    }
//
//    override fun createTransactionSelects(): Observable<Unit> = createTransactionButton.clicks()
//    override fun transactionsSelects(): Observable<Unit> = transactionsOverviewView.clicks()
//    override fun tagsSelects(): Observable<Unit> = toolbarClicks.filter { it.itemId == R.id.action_tags }.map { Unit }
//    override fun trendsReportSelects(): Observable<Unit> = trendsReportView.clicks()
//    override fun tagsReportSelects(): Observable<Unit> = tagsReportOverviewView.clicks()
//    override fun settingsSelects(): Observable<Unit> = toolbarClicks.filter { it.itemId == R.id.action_settings }.map { Unit }
//    override fun showInterval(interval: Interval, reportPeriod: ReportPeriod) = with(supportActionBar) { title = dateFormatter.formatInterval(reportPeriod, interval) }
//    override fun displayCreateTransaction(): Unit = CalculatorActivity.start(this)
//    override fun displayTransactions(): Unit = TransactionsActivity.start(this)
//    override fun displayTags(): Unit = TagsActivity.startView(this)
//    override fun displayTrendsReport(): Unit = TrendsReportActivity.start(this)
//    override fun displayTagsReport(): Unit = TagsReportActivity.start(this)
//    override fun displaySettings(): Unit = SettingsActivity.start(this)
//
//    private fun removeUpArrowFromToolbar() {
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        val keyline = resources.getDimensionPixelSize(R.dimen.keyline)
//        toolbar.setContentInsetsRelative(keyline, keyline)
//    }
}