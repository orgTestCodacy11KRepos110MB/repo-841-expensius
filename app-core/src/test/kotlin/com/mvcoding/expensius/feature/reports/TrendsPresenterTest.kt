/*
 * Copyright (C) 2017 Mantas Varnagiris.
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

package com.mvcoding.expensius.feature.reports

import com.mvcoding.expensius.data.Cache
import com.mvcoding.expensius.data.DataSource
import com.mvcoding.expensius.feature.reports.trends.TrendsPresenter
import com.mvcoding.expensius.model.RemoteFilter
import com.mvcoding.expensius.model.ReportSettings
import com.mvcoding.expensius.model.Trends
import com.mvcoding.expensius.model.extensions.aRemoteFilter
import com.mvcoding.expensius.model.extensions.aReportSettings
import com.mvcoding.expensius.model.extensions.aTrends
import com.mvcoding.expensius.rxSchedulers
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import rx.Observable.error
import rx.Observable.just

class TrendsPresenterTest {

    val trends = aTrends()
    val reportSettings = aReportSettings()
    val remoteFilter = aRemoteFilter(reportSettings.reportPeriod)

    val trendsSource = mock<DataSource<Trends>>()
    val reportSettingsSource = mock<DataSource<ReportSettings>>().apply { whenever(data()).thenReturn(just(reportSettings)) }
    val secondaryRemoteFilterCache = mock<Cache<RemoteFilter>>().apply { whenever(data()).thenReturn(just(remoteFilter)) }
    val view = mock<TrendsPresenter.View>()
    val presenter = TrendsPresenter(trendsSource, reportSettingsSource, secondaryRemoteFilterCache, rxSchedulers())

    @Before
    fun setUp() {
        whenever(trendsSource.data()).thenReturn(just(trends))
    }

    @Test
    fun `secondary remote filter points to previous period`() {
        verify(secondaryRemoteFilterCache).write(remoteFilter.withPreviousInterval(reportSettings.reportPeriod))
    }

    @Test
    fun `shows trends`() {
        presenter.attach(view)

        verify(view).showTrends(trends)
    }

    @Test
    fun `ignores errors`() {
        whenever(trendsSource.data()).thenReturn(error(Throwable()))

        presenter.attach(view)

        verify(view, never()).showTrends(any())
    }
}