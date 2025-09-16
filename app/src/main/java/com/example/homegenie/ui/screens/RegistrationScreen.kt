package com.example.homegenie.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.RegistrationDetails
import com.example.homegenie.ui.viewmodels.RegistrationScreenViewModel
import com.example.homegenie.ui.viewmodels.RegistrationUiState
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navigateToMain: () -> Unit,
) {

    val registrationViewModel: RegistrationScreenViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Blue001,
                contentColor = Color.White,
                shape = RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp, 10.dp, 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Register with us!")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            RegistrationBody(
                registrationUiState = registrationViewModel.registrationUiState,
                onItemValueChange = registrationViewModel::updateRegistrationUiState,
                onSaveClick = {
                    coroutineScope.launch(Dispatchers.Main) {
                        registrationViewModel.insert(
                            CustomerDetailsModel.CustomerItem(
                                Firebase.auth.currentUser?.uid,
                                registrationViewModel.registrationUiState.registrationDetails.name,
                                registrationViewModel.registrationUiState.registrationDetails.gender,
                                registrationViewModel.registrationUiState.registrationDetails.age.toLong(),
                                registrationViewModel.registrationUiState.registrationDetails.mobileNumber.toLong(),
                            )
                        ).collect {
                            when (it) {
                                is ResultState.Success -> {
                                    context.showMsg("Registration Successful")
                                    navigateToMain()
                                }

                                is ResultState.Failure -> {
                                    context.showMsg(it.msg.toString())
                                }

                                ResultState.Loading -> {
                                    //isLoading = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

    }
}

@Composable
fun RegistrationBody(
    registrationUiState: RegistrationUiState,
    onItemValueChange: (RegistrationDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        RegistrationForm(
            registrationDetails = registrationUiState.registrationDetails,
            onValueChange = onItemValueChange
        )
        Button(
            onClick = onSaveClick,
            enabled = registrationUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm(
    registrationDetails: RegistrationDetails,
    //modifier: Modifier,
    onValueChange: (RegistrationDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = registrationDetails.name,
            onValueChange = { onValueChange(registrationDetails.copy(name = it)) },
            label = { Text("Name *", style = MaterialTheme.typography.bodySmall) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = MaterialTheme.typography.bodySmall
        )


        OutlinedTextField(
            value = registrationDetails.age,
            onValueChange = { onValueChange(registrationDetails.copy(age = it)) },
            label = { Text("Age *", style = MaterialTheme.typography.bodySmall,
            ) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodySmall,
            supportingText = {
                if(registrationDetails.age.length > 3){
                    Text(text = "Invalid age!!", color = Color.Red, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
                }
            }
        )

        val gender = listOf("Male", "Female", "Others")
        var expanded by remember { mutableStateOf(false) }
        var selectedText by remember { mutableStateOf(gender[0]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {  },
                readOnly = true,
                label = { Text("Gender *", style = MaterialTheme.typography.bodySmall) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    /*Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true })*/
                },
                //enabled = enabled,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                gender.forEach() { item ->
                    DropdownMenuItem(text = { Text(text = item, style = MaterialTheme.typography.bodySmall) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onValueChange(registrationDetails.copy(gender = selectedText))
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = registrationDetails.mobileNumber,
            onValueChange = { onValueChange(registrationDetails.copy(mobileNumber = it)) },
            label = { Text("Mobile Number *", style = MaterialTheme.typography.bodySmall) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodySmall,
            supportingText = {
                if(registrationDetails.mobileNumber.length > 10){
                    Text(text = "Invalid mobile number!!", color = Color.Red, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
                }
            }
        )

    }
}