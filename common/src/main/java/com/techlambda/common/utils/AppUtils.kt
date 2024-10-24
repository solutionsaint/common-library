package com.techlambda.common.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

fun getRequestBody(data : String) = data.toRequestBody("text/plain".toMediaTypeOrNull())

fun getMultipartBody(context: Context, imageUri: Uri, paramName: String = "file"): MultipartBody.Part {
    val fileDir = context.filesDir
    val file = File(fileDir, "image.png")
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val part = MultipartBody.Part.createFormData(paramName, file.name, requestBody)
    return part
}
