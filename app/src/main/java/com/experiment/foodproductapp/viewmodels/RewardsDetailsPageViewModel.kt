package com.experiment.foodproductapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.experiment.foodproductapp.constants.CardFace

class RewardsDetailsPageViewModel : ViewModel() {

    private val _bronzeCardFace = mutableStateOf(CardFace.Front)
    val bronzeCardFace = _bronzeCardFace

    private val _silverCardFace = mutableStateOf(CardFace.Front)
    val silverCardFace = _silverCardFace

    private val _goldCardFace = mutableStateOf(CardFace.Front)
    val goldCardFace = _goldCardFace

    private val _rewardPoints = mutableStateOf(0)
    val rewardPoints = _rewardPoints

    private val _bronzeBorder = mutableStateOf(0.dp)
    val bronzeBorder = _bronzeBorder

    private val _silverBorder = mutableStateOf(0.dp)
    val silverBorder = _silverBorder

    private val _goldBorder = mutableStateOf(0.dp)
    val goldBorder = _goldBorder

    private val _bronzeBroderAni = mutableStateOf(false)
    val bronzeBorderAni = _bronzeBroderAni

    private val _silverBroderAni = mutableStateOf(false)
    val silverBorderAni = _silverBroderAni

    private val _goldBroderAni = mutableStateOf(false)
    val goldBorderAni = _goldBroderAni

    fun onBronzeClick(cardFace: CardFace) {
        if (cardFace== CardFace.Back)
        {
            _silverCardFace.value = CardFace.Front
            _goldCardFace.value=CardFace.Front
        }
    }
    fun onSilverClick(cardFace: CardFace) {
        if (cardFace== CardFace.Back)
        {
            _bronzeCardFace.value = CardFace.Front
            _goldCardFace.value=CardFace.Front
        }
    }
    fun onGoldClick(cardFace: CardFace) {
        if (cardFace== CardFace.Back)
        {
            _silverCardFace.value = CardFace.Front
            _bronzeCardFace.value= CardFace.Front
        }
    }

    fun setBorder() {
        when (rewardPoints.value) {
            in 0..500 -> {
                _bronzeBorder.value=0.5.dp
                _bronzeBroderAni.value = true
            }
            in 501..1000 -> {
                _silverBorder.value=0.5.dp
                _silverBroderAni.value = true
            }
            else -> {
                _goldBorder.value=0.5.dp
                _goldBroderAni.value = true
            }
        }
    }
}
