package com.techlambda.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.techlambda.common.ui.CommonButton
import com.techlambda.common.ui.CommonListScreenContent
import com.techlambda.common.ui.ExitDialog
import com.techlambda.common.ui.theme.CommonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CommonListScreenContent(
                        context = this,
                        name = "List Screen",
                        isAdmin = true,
                        navigateToAddScreen = {},
                        uniqueId = "ABCDEF",
                        navigateToSettingScreen = {},
                        list = listOf("Hello"),
                        topAppBarBackgroundColor = MaterialTheme.colorScheme.primary
                    ) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        var showAlertDialog by remember { mutableStateOf(false) }
                        BackHandler(enabled = true) {
                            showAlertDialog = true
                        }
                        if(showAlertDialog) {
                            ExitDialog(onDismiss = { showAlertDialog=false }) {
                                this.finish()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CommonTheme {
        Greeting("Android")
    }
}