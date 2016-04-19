/*
 * Copyright (C) 2016 Mantas Varnagiris.
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

package com.mvcoding.expensius.feature.premium

import com.memoizrlabs.Shank.registerFactory
import com.memoizrlabs.ShankModule
import com.mvcoding.expensius.provideContext
import com.mvcoding.expensius.provideSettings

class PremiumModule : ShankModule {
    override fun registerFactories() {
        premiumPresenter()
    }

    private fun premiumPresenter() {
        registerFactory(PremiumPresenter::class.java) { ->
            val remotePremiumService = BillingRemoteBillingProductsService(provideContext())
            val billingProductsProvider = BillingProductsProvider(remotePremiumService)
            PremiumPresenter(provideSettings(), billingProductsProvider)
        }
    }
}