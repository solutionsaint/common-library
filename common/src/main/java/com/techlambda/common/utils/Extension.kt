package com.techlambda.common.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.viewOrDownloadFile(fileUrl: String) {
    // TODO: TEMP FIX, TO BE FIXED BY BACKEND
    val uri = Uri.parse(fileUrl.replace(":3293", ":3294").replace("s://", "://"))
    val mimeType = when {
        fileUrl.endsWith(".jpg", true) || fileUrl.endsWith(".jpeg", true) -> "image/jpeg"
        fileUrl.endsWith(".png", true) -> "image/png"
        fileUrl.endsWith(".pdf", true) -> "application/pdf"
        else -> null
    }

    try {
        mimeType?.let {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, it)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(Intent.createChooser(intent, "Open with"))
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(webIntent)
        }
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_LONG).show()
    }
}