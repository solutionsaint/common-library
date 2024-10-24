package com.techlambda.common.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.techlambda.common.AdminHeader
import com.techlambda.common.R

@Composable
fun CommonListScreenContent(
    isAdmin: Boolean,
    topAppBarBackgroundColor: Color,
    navigateToAddScreen: () -> Unit,
    name: String,
    uniqueId: String,
    context: Context,
    list: List<Any>,
    itemContent: @Composable (Any) -> Unit
) {
    var shareCode by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTopAppBar(
            isAdmin = isAdmin,
            backgroundColor = topAppBarBackgroundColor,
            navigateToAddScreen = navigateToAddScreen,
            isListScreen = true,
            isEdit = false,
            navigateToBack = {}
        ) { paddingValues ->
            Column {
                if (isAdmin) {
                    val clipboardManager = LocalClipboardManager.current
                    AdminHeader(
                        titleText = name,
                        bodyText = uniqueId,
                        modifier = Modifier.padding(paddingValues),
                        icon1 = R.drawable.ic_copy,
                        icon2 = R.drawable.ic_share,
                        onIcon1Clicked = {
                            clipboardManager.setText(AnnotatedString(uniqueId))
                            Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onIcon2Clicked = {
                            shareCode = true
                        }
                    )
                }
                LazyColumn(
                    Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    items(list) {
                        itemContent(it)
                    }
                }
            }
        }
    }
    if (uniqueId.isNotBlank()) {
        val shareIntent = remember {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, uniqueId)
                type = "text/plain"
            }
        }


        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {}
        )
        if (shareCode) {
            launcher.launch(Intent.createChooser(shareIntent, "Share via"))
            shareCode = false
        }
    }
}