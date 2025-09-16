package com.example.homegenie.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.repositories.GenieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(private val repository: GenieRepository) : ViewModel() {

    var registrationUiState by mutableStateOf(RegistrationUiState())
        private set


    fun updateRegistrationUiState(registrationDetails: RegistrationDetails) {
        registrationUiState = RegistrationUiState(
            registrationDetails = registrationDetails, isEntryValid = validateInput(registrationDetails)
        )
    }

    private fun validateInput(uistate: RegistrationDetails = registrationUiState.registrationDetails): Boolean {
        return with(uistate) {
            name.isNotBlank() && gender.isNotBlank() && age.isNotBlank() && (mobileNumber.length == 10)
                    && ((mobileNumber.toLongOrNull() ?: 0L) != 0L) && ((age.toLongOrNull() ?: 0L) != 0L)
        }
    }

    fun insert(item: CustomerDetailsModel.CustomerItem) = repository.insertC(item)

    fun delete(key:String) = repository.deleteC(key)

    fun update(item: CustomerDetailsModel) = repository.updateC(item)

}

data class RegistrationUiState(
    val registrationDetails: RegistrationDetails = RegistrationDetails(),
    val isEntryValid : Boolean = false
)

data class RegistrationDetails(
    var name: String = "",
    var gender: String = "",
    var age: String = "",
    var mobileNumber: String = "",
)
