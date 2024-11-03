package com.techlambda.common.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.techlambda.attendanceapp.ui.common.ImageSelectionDialog
import com.techlambda.common.R
import com.techlambda.common.utils.ImageSelectionDialog
import com.techlambda.common.utils.showToast

@Composable
fun CardText(
    text: String,
    isBold: Boolean = false
) {
    Text(
        text = text,
        fontWeight = if (isBold) FontWeight.Bold else null,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun InputField(
    label: String,
    modifier: Modifier = Modifier,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF7F8F9),
                unfocusedContainerColor = Color(0xFFF7F8F9),
                focusedIndicatorColor = Color(0xFFD9DDE3),
                unfocusedIndicatorColor = Color(0xFFD9DDE3)
            ),
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
fun OutlinedInputField(
    label: String, modifier: Modifier = Modifier, value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 6.dp),
        )
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier, onClick = onClick, shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun CustomImage(
    imageUrl: String? = null,
    placeholder: ImageVector = Icons.Default.Image,
    modifier: Modifier = Modifier
        .size(80.dp),
    contentScale: ContentScale = ContentScale.Fit,
    isEditEnabled: Boolean = true,
    onImageSelected: (Uri?) -> Unit
) {
    var showPopup by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf(imageUrl) }
    Box(modifier = modifier) {
        val painter = if (imageUri == null || imageUri == "") {
            rememberVectorPainter(placeholder)
        } else {
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .error(R.drawable.error)
                    .build()
            )
        }
        Image(
            painter = painter,
            contentDescription = "NetworkImage with url $imageUrl",
            modifier = modifier.clickable { if (isEditEnabled) showPopup = true },
            contentScale = contentScale,
        )
    }

    if (showPopup) {
        ImageSelectionDialog(
            onDismiss = { showPopup = false },
            onImageSelected = {
                if (it != null) {
                    imageUri = it.toString()
                    onImageSelected(it)
                }
            })
    }
}


@Composable
fun UploadImageField(
    modifier: Modifier = Modifier,
    label: String,
    imageUrl: String,
    onCLick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(
            text = "Upload Image",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(6.dp))
                .padding(16.dp)
                .clickable {
                    onCLick()
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = imageUrl,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Upload,
                contentDescription = label,
                tint = Color.Blue
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    isListScreen: Boolean,
    isEdit: Boolean,
    isAdmin: Boolean,
    context: Context,
    backgroundColor: Color,
    navigateToAddScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToBack: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        Column {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 10.dp),
                title = {
                    Text(
                        if (isListScreen) "List View & Add" else if (isEdit) "Edit" else "Add",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Row {
                        if (isAdmin && isListScreen) {
                            IconButton(modifier = Modifier
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .background(color = backgroundColor),
                                onClick = {
                                    navigateToAddScreen()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.White
                                )
                            }
                        } else if(!isListScreen) {
                            TextButton(onClick = { navigateToBack() }) {
                                Text(text = "Cancel", fontWeight = FontWeight.SemiBold)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(color = backgroundColor),
                            onClick = {
                                navigateToSettingScreen()
                            }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(color = backgroundColor),
                            onClick = {
                                val deepLinkUri = Uri.parse("myapp://raise_enquiry/owner")
                                val intent = Intent(Intent.ACTION_VIEW, deepLinkUri)

                                // Check if there is an app that can handle this deep link
                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                } else {
                                    context.showToast("App not install. Please install Enquiry app to raise enquiry")
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = "Enquiry",
                                tint = Color.White
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (!isListScreen) {
                        IconButton(onClick = { navigateToBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
            )
        }
    }) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun CommonContentCard(
    modifier: Modifier,
    imageUrl: String?,
    isAdmin: Boolean,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 14.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (imageUrl != null) {
                CustomImage(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(50.dp),
                    imageUrl = imageUrl,
                    contentScale = ContentScale.FillBounds
                ) {}
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                content()
                if (isAdmin) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red,
                            modifier = Modifier.clickable { onDeleteClicked() }
                        )
                    }
                }
            }
            if (isAdmin) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { onEditClicked() }
                )
            }
        }
    }
}


@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(text = {
                    Text(modifier = Modifier.fillMaxWidth(), text = option)
                }, onClick = {
                    selectedText = option
                    onOptionSelected(option)
                    expanded = false
                })

            }
        }
    }
}
