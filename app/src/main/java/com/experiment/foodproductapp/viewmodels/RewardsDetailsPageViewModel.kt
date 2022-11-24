package com.experiment.foodproductapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
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

    private val _bronzePadding = mutableStateOf(5.dp)
    val bronzePadding = _bronzePadding

    private val _silverPadding = mutableStateOf(5.dp)
    val silverPadding = _silverPadding

    private val _goldPadding = mutableStateOf(5.dp)
    val goldPadding = _goldPadding

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
            _bronzeCardFace.value=CardFace.Front
        }
    }

    fun setBorder() {
        when (rewardPoints.value) {
            in 0..500 -> {
                _bronzeBorder.value=5.dp
                _bronzePadding.value=0.dp
            }
            in 501..1000 -> {
                _silverBorder.value=5.dp
                _silverPadding.value=0.dp
            }
            else -> {
                _goldBorder.value=5.dp
                _goldPadding.value=0.dp
            }
        }
    }


    enum class CardFace(val angle: Float) {
        Front(0f) {
            override val next: CardFace
                get() = Back
        },
        Back(180f) {
            override val next: CardFace
                get() = Front
        };

        abstract val next: CardFace
    }
}
