package com.example.core.mvi.impl

import com.example.common.navigation.DataBundle
import com.example.core.mvi.api.MviNavigationEvent

sealed interface BaseNavigationEvent : MviNavigationEvent {

    interface NavigateTo : BaseNavigationEvent {
        val route: String
        val dataBundle: DataBundle
    }

    interface NavigateBackTo : BaseNavigationEvent {
        val route: String
    }

    interface NavigateBack : BaseNavigationEvent
}