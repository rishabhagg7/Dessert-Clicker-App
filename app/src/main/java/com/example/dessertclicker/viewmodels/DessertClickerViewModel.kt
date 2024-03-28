package com.example.dessertclicker.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.DataSource
import com.example.dessertclicker.uistatemodels.DessertClickerUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DessertClickerUiModel())
    val uiState = _uiState.asStateFlow()
    private val desserts = DataSource.dessertList

    init {
        updateDessertUiState(
            currentDessertIndex = 0,
            totalRevenue = 0,
            dessertsSold = 0
        )
    }

    fun onDesertClicked(){
        var currentIndex = _uiState.value.currentDessertIndex
        val currentDessertPrice = desserts[currentIndex].price
        val currentDessertsSold = _uiState.value.dessertsSold
        val currentTotalRevenue = _uiState.value.totalRevenue
        val newTotalRevenue = currentTotalRevenue.plus(currentDessertPrice)
        val newDessertsSold = currentDessertsSold.inc()

        if(currentIndex < desserts.size-1 && newDessertsSold >= desserts[currentIndex+1].startProductionAmount){
            currentIndex = currentIndex.inc()
        }

        updateDessertUiState(
            currentDessertIndex = currentIndex,
            totalRevenue = newTotalRevenue,
            dessertsSold = newDessertsSold
        )
    }

    private fun updateDessertUiState(
        currentDessertIndex: Int,
        totalRevenue: Int,
        dessertsSold: Int
    ) {
        _uiState.update { currentState->
            currentState.copy(
                currentDessertIndex = currentDessertIndex,
                totalRevenue = totalRevenue,
                dessertsSold = dessertsSold
            )
        }
    }
}