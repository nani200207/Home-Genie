package com.example.homegenie.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homegenie.R
import com.example.homegenie.ui.viewmodels.LoginScreenViewModel
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToRegistration: () -> Unit,
    //loginScreenViewModel: LoginScreenViewModel = HiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = (configuration.screenHeightDp.dp) * 0.4f


    val isLoading = remember {
        mutableStateOf(false)
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.logg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenHeight),
                alignment = Alignment.TopCenter
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Home Utilities",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Booking App",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(30.dp))
                Divider(modifier = Modifier.width(screenWidth * 0.15f))
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Login or Signup",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(20.dp))

                GoogleAuth(
                    navigateToRegistration = navigateToRegistration,
                    navigateToHome = navigateToHome,
                    //viewModel = loginScreenViewModel,
                    scope = coroutineScope,
                    isLoading = isLoading
                )

//                Button(
//                    onClick = {
//
//                    },
//                    elevation = ButtonDefaults.buttonElevation(5.dp),
//
//                    content = {
//                        //Your Button Design
//
//                        Image(
//                            painter = painterResource(id = R.drawable.google_logo),
//                            contentDescription = "Login with Google",
//                            modifier = Modifier.size(30.dp)
//                        )
//                        Spacer(modifier = Modifier.width(5.dp))
//                        Text("Login with Google", style = MaterialTheme.typography.labelSmall)
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                )


                Spacer(modifier = Modifier.height(30.dp))
                Divider(modifier = Modifier.width(screenWidth * 0.15f))
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "By continuing, you agree to our",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp
                )
                Text(
                    text = "Terms of Service, Privacy Policy, Content Policy",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp
                )

                /*
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                RoundButton(
                                    text = stringResource(R.string.google_logo),
                                    image = painterResource(id = R.drawable.google_logo)
                                ) {

                                }

                                RoundButton(
                                    text = stringResource(R.string.three_dots_logo),
                                    image = painterResource(id = R.drawable.ic_three_dots)
                                ) {
                                    /*TODO*/
                                }
                            }

                 */


            }
        }
        if (isLoading.value) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun GoogleAuth(
    navigateToRegistration: () -> Unit,
    navigateToHome: () -> Unit,
    //viewModel: LoginScreenViewModel,
    scope: CoroutineScope,
    isLoading: MutableState<Boolean>
) {

    val token =
        stringResource(R.string.default_web_client_id)//Will be generated after Firebase Integration
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: LoginScreenViewModel = hiltViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->//Here, we get the Intent result
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //navigateToHome()
                        scope.launch(Dispatchers.Main) {
                            viewModel.checkItem().collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        isLoading.value = false
                                        if (it.data) {
                                            navigateToHome()
                                        } else {
                                            navigateToRegistration()
                                        }
                                    }

                                    is ResultState.Failure -> {
                                        context.showMsg(it.msg.toString())
                                        isLoading.value = false

                                    }

                                    ResultState.Loading -> {

                                        isLoading.value = true

                                    }
                                }
                            }
                        }
                        //Here, do whatever you want to do after successful auth
                    } else {
                        //Here, handle failure
                    }
                }

        } catch (e: ApiException) {
            Log.e("TAG", "Google sign in failed", e)
        }
    }

    Button(
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()//Creating GSO object
            //val context = LocalContext.current <- Outside of This button composition
            val googleSignInClient = GoogleSignIn.getClient(context, gso)//Get client obj
            launcher.launch(googleSignInClient.signInIntent)//Launch the intent using a launcher that we will create now.
        },
        elevation = ButtonDefaults.buttonElevation(5.dp),

        content = {
            //Your Button Design

            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Login with Google",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text("Login with Google", style = MaterialTheme.typography.labelSmall)
        },
        modifier = Modifier.fillMaxWidth()
    )


}



