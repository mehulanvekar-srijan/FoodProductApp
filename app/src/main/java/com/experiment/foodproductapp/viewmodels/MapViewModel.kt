package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlin.math.sqrt

class MapViewModel : ViewModel() {

    fun findShortestDistance(
        currentLocation: LatLng,
        destinations: List<LatLng>
    ): LatLng {

        val distance = MutableList(0) { 0.0 }

        destinations.forEach {
            val d = sqrt( ((it.latitude - currentLocation.latitude) * (it.latitude - currentLocation.latitude)) + ((it.longitude - currentLocation.longitude) * (it.longitude - currentLocation.longitude)) )
            distance.add(d)
        }

        var shortestDistance = distance[0]
        var index = 0
        for(i in 0 until distance.size){
            if (distance[i] < shortestDistance) {
                shortestDistance = distance[i]
                index = i
            }
        }

        Log.d("testMap", " = $distance")
        Log.d("testMap", " shortestDistance = $shortestDistance , index = $index")

        return destinations[index]
    }

}