package com.example.homegenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homegenie.repositories.GenieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val repository: GenieRepository) : ViewModel() {

    fun checkItem() = repository.checkItemC()

}