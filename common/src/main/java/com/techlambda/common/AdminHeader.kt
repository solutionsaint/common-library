package com.techlambda.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminHeader(
    titleText: String,
    bodyText: String,
    modifier: Modifier = Modifier,
    icon1: Int,
    icon2: Int,
    onIcon1Clicked: () -> Unit,
    onIcon2Clicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titleText,
            fontSize = 20.sp
        )
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = bodyText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )

            IconButton(onClick = {
                onIcon1Clicked()
            }) {
                Icon(
                    painter = painterResource(id = icon1),
                    contentDescription = "Icon1",
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(onClick = {
                onIcon2Clicked()
            }) {
                Icon(
                    painter = painterResource(id = icon2),
                    contentDescription = "Icon2",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}