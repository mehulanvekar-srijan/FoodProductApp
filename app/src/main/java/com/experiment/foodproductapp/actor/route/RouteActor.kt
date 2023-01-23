package com.experiment.foodproductapp.actor.route

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import com.experiment.foodproductapp.stream.Message
import com.experiment.foodproductapp.stream.State
import com.experiment.foodproductapp.actor.ActorFunction

import com.experiment.foodproductapp.actor.TheActorScope
import com.experiment.foodproductapp.actor.route.RouteState.*
import com.experiment.foodproductapp.constants.Screen

data class GetRouteState(
    val state: CompletableDeferred<RouteState> = CompletableDeferred()
) : Message

//data class Navigate(val fragment: Fragment, val path: String) : Message
data class NavigateObj(val route: String) : Message

sealed class RouteState : State {
    object NotNavigated : RouteState()
    //data class NavigateToTab(val tabIndex: Int, val nestedPath: String): RouteState()
    data class NavigateTo(val route: String): RouteState()
    object NavigatedSuccessfully: RouteState()
}

fun routeActor(): ActorFunction<RouteState> {

    var screens: MutableList<String>

//    fun performNavigation(fragment: Fragment, path: String): RouteState {
//
//        screens = path.split("/") as MutableList<String>
//        if (screens[0] == "bottomTab") {
//            when (screens[1]) {
//                "home" -> {
//                    if (screens[2] == "profile") {
//                        return NavigateToTab(0,"${screens[1]}/profile")
//                    }
//                }
//
//                "notification" -> {
//                    if (screens[2] == "resetPassword") {
//                        return NavigateToTab(3,"${screens[1]}/${screens[2]}")
//                    }
//                }
//                else -> { return NotNavigated }
//            }
//        }
//        return NotNavigated
//    }

    fun performNavigation(route: String): RouteState {

        when(route) {

            Screen.SignUpScreen.route -> { return NavigateTo(route = Screen.SignUpScreen.route) }
            Screen.ForgotPassword.route -> { return NavigateTo(route = Screen.ForgotPassword.route) }
            Screen.HomeScreen.route -> { return NavigateTo(route = Screen.HomeScreen.route) }
            Screen.ProductDetailsScreen.route -> { return NavigateTo(route = Screen.ProductDetailsScreen.route) }
            else -> { return NotNavigated }

        }

        return NotNavigated

    }

    tailrec suspend fun TheActorScope.function(state: RouteState) {

        Log.d("testActors", "3.0. TheActorScope.function : called state=$state")

        when (val message = channel.receive()) {    // 3. block the execution and catch the event sent by Fragments (e.g GamesFragment)
            is NavigateObj -> {
                //val navState = performNavigation(message.fragment, message.path)
                val navState = performNavigation(message.route)
                Log.d("testActors", "3.1.TheActorScope.function : Navigate : called message=$message and state=$state channel=$channel")
                function(navState)                 // 4. Restart the function again
            }

            is GetRouteState -> {
                message.state.complete(state)
                Log.d("testActors", "3.2.TheActorScope.function : GetRouteState : called message=$message and state=$state")
                function(state)
            }

            else -> function(state)
        }
    }
    return TheActorScope::function
}