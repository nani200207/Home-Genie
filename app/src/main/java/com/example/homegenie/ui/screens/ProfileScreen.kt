package com.example.homegenie.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homegenie.R
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.ProfileScreenViewModel
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigate: () -> Unit
){
    val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
    val response = profileScreenViewModel.response

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var open by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Spacer(modifier = Modifier.padding(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
            border = BorderStroke(1.dp, Color.LightGray)

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(25.dp, 15.dp)
            ) {

                // if (response.data.item?.name != null && response.data.item?.name!!.isNotBlank()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = response.data.item?.name!!,
                            style = MaterialTheme.typography.bodyMedium,
                            //textAlign = TextAlign.Center,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Divider(modifier = Modifier.width(50.dp))
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //if (response.data.item?.name != null && response.data.item?.name!!.isNotBlank()) {
                    //Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = response.data.item?.age.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = response.data.item?.gender!!,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = response.data.item.mobileNumber.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = Blue001,
                            modifier = Modifier.size(100.dp)
                        )

                    }
                    //Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {open = true}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Edit Profile", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Firebase.auth.signOut()
                    navigate()
                },
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start, modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_logout_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Logout", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    if (open) {

        var name by remember {
            mutableStateOf(response.data.item?.name!!)
        }

        var age by remember {
            mutableStateOf(response.data.item?.age.toString())
        }
        var gender by remember {
            mutableStateOf(response.data.item?.gender!!)
        }
        var mobileNumber by remember {
            mutableStateOf(response.data.item?.mobileNumber.toString())
        }

        Dialog(onDismissRequest = { open = false }) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(30.dp)
                ) {
                    Text(
                        text = "Edit Profile!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    //Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(
                                "Name *",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        textStyle = MaterialTheme.typography.bodySmall
                    )

                    OutlinedTextField(
                        value = age,
                        onValueChange = {
                            age = it
                        },
                        label = {
                            Text(
                                "Age *",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.bodySmall
                    )

                    val genderList = listOf("Male", "Female", "Others")
                    var expanded by remember { mutableStateOf(false) }
                    var selectedText by remember { mutableStateOf(response.data.item?.gender!!) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            value = selectedText,
                            onValueChange = { },
                            readOnly = true,
                            label = {
                                Text(
                                    "Gender *",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
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
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            genderList.forEach() { item ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                    onClick = {
                                        selectedText = item
                                        expanded = false
                                        gender = selectedText
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = {
                            mobileNumber = it
                        },
                        label = {
                            Text(
                                "Mobile Number *",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.bodySmall
                    )

                    //Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                profileScreenViewModel.update(
                                    CustomerDetailsModel(
                                        item = CustomerDetailsModel.CustomerItem(
                                            name = name,
                                            gender = gender,
                                            age = age.toLong(),
                                            mobileNumber = mobileNumber.toLong()
                                        ),
                                        key = Firebase.auth.currentUser?.uid
                                    )
                                ).collect {
                                    when (it) {
                                        is ResultState.Success -> {
                                            context.showMsg("Success!")
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
                            open = false
                        },
                        enabled = name.isNotBlank() && age.isNotBlank() && gender.isNotBlank() && mobileNumber.isNotBlank() && ((age.toLongOrNull()
                            ?: 0L) != 0L) && ((mobileNumber.toLongOrNull()
                            ?: 0L) != 0L) && (mobileNumber.length == 10)
                    ) {
                        Text(
                            text = "Save Changes",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }


}