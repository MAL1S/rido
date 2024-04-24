package com.example.core.mvi.impl

import androidx.annotation.CallSuper
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Model
import com.example.core.mvi.api.ModelNavigationEvent
import com.example.core.mvi.api.ModelState
import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.api.State
import com.example.core.mvi.api.withState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseModel<ViewState, ViewAction, NavEvent>(
    defaultViewState: ViewState,
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val modelState: ModelState<ViewState> = BaseModelState(defaultViewState, scope),
    private val modelNavigationEvent: ModelNavigationEvent<NavEvent> = BaseModelNavigationEvent(scope)
) : Model<ViewState, ViewAction, NavEvent>
    where ViewState : State,
          ViewAction : Action,
          NavEvent : MviNavigationEvent {

    override val viewState: StateFlow<ViewState>
        get() = modelState.viewState
    override val navigationEvent: Flow<NavEvent>
        get() = modelNavigationEvent.navigationEvent

    override fun sendNavigationEvent(navEvent: NavEvent) {
        modelNavigationEvent.sendNavigationEvent(navEvent)
    }

    protected fun updateState(updateViewState: ViewState.() -> ViewState) {
        modelState.updateState(updateViewState)
    }

    protected fun withState(withViewState: ViewState.() -> Unit) {
        modelState.withState(withViewState)
    }

    @CallSuper
    override fun clean() {
        //TODO: cancel() or cancelChildren()
        scope.cancel()
    }
}
