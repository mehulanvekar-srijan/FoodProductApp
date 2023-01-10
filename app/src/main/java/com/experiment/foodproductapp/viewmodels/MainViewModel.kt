package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.experiment.foodproductapp.actor.route.GetRouteState
import com.experiment.foodproductapp.actor.route.RouteState
import com.experiment.foodproductapp.stream.AppStream
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class NavigationUIMessages {
    //data class SelectTabAndNavigate( val tabIndex: Int, val nestedPath: String ) : NavigationUIMessages()
    data class NavigateTo(val route : String) : NavigationUIMessages()
    object SkipNavigation : NavigationUIMessages()
}

class MainViewModel : ViewModel() {

    //will be used to perform actual navigation
    private val _uiMessagesFlow: MutableSharedFlow<NavigationUIMessages> =
        MutableSharedFlow(extraBufferCapacity = 100, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val uiMessages = _uiMessagesFlow.asSharedFlow()


    suspend fun getNavigationState() {

        Log.d("testActors", "4.0 getNavigationState : called")

        when (val state = fetchNavigationState()) { // 6. actor will return current Nav State, accordingly emmit event MainFragment
            is RouteState.NavigateTo -> {
                Log.d("testActors", "4.2. getNavigationState : emitting state=$state")
                _uiMessagesFlow.emit(NavigationUIMessages.NavigateTo(state.route))
            }
            RouteState.NotNavigated -> {
                _uiMessagesFlow.emit(NavigationUIMessages.SkipNavigation)
            }
            else -> {
                _uiMessagesFlow.emit(NavigationUIMessages.SkipNavigation)
            }
        }

    }

    private suspend fun fetchNavigationState(): RouteState {    // 5. Get the current Navigation state of app from actor
        val defState = CompletableDeferred<RouteState>()
        AppStream.send((GetRouteState(defState)))
        return defState.await()
    }
}
