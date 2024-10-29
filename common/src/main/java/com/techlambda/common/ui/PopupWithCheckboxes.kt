package com.techlambda.attendanceapp.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PopupWithCheckboxes(
    title: String = "Select Items",
    items: List<String>,
    selectedItem: List<String>,
    onItemSelected: (List<String>) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedItems by remember { mutableStateOf(selectedItem.toMutableList()) }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(text = title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        },
        text = {
            Column {
                items.forEach { day ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedItems.contains(day),
                            onCheckedChange = {
                                selectedItems = if (selectedItems.contains(day)) {
                                    // Create a new list with the item removed
                                    selectedItems.toMutableList().apply { remove(day) }
                                } else {
                                    // Create a new list with the item added
                                    selectedItems.toMutableList().apply { add(day) }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = day, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // Pass the updated selected items list back to the parent composable
                onItemSelected(selectedItems)
                onDismissRequest()
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancel")
            }
        }
    )
}

