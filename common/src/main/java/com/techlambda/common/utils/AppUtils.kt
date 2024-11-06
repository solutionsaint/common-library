package com.techlambda.common.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
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
fun getStringFromUris(context: Context,uris: List<Uri>): String {
    val stringBuilder = StringBuilder()
    for (uri in uris) {
        stringBuilder.append(getFileName(context, uri)).append("\n")
    }
    if(stringBuilder.length>1) {
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
    }
    return stringBuilder.toString()
}

fun getMultipartBodyList(context: Context, fileUris: List<Uri>, paramName: String = "file"): List<MultipartBody.Part> {
    val parts = mutableListOf<MultipartBody.Part>()
    for (uri in fileUris) {
        val fileDir = context.filesDir
        val fileName = getFileName(context, uri)
        val file = File(fileDir, fileName)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(paramName, fileName, requestBody)
        parts.add(part)
    }
    return parts
}

fun getFileName(context: Context, uri: Uri?): String {
    var result: String? = null
    if (uri?.scheme == "content") {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                result = cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null && uri != null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result ?: ""
}


suspend fun getImageUrisFromUrls(context: Context,urls: List<String>): List<Uri> {
    val uris = mutableListOf<Uri>()
    urls.forEach { url ->
        downloadImageToInternalStorage(context, url)?.let { uris.add(it) }
    }
    return uris
}
suspend fun downloadImageToInternalStorage(context: Context, imageUrl: String): Uri? {
    return withContext(Dispatchers.IO) {
        try {
            // Create the OkHttpClient
            val client = OkHttpClient()
            val request = Request.Builder().url(imageUrl).build()

            // Execute the request
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    val folder = File(context.filesDir.path+"/internal_images")
                    if (!folder.exists()) {
                        folder.mkdirs()
                    }

                    // Create a file in the internal storage
                    val file = File(folder, getFileNameFromUrl(imageUrl))
                    val outputStream = FileOutputStream(file)
                    inputStream.copyTo(outputStream)
                    outputStream.close()

                    // Return the URI of the saved file
                    Uri.fromFile(file)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

fun getFileNameFromUrl(url: String): String {
    return url.substringAfterLast("/")
}

fun deleteFileFromInternalStorage(context: Context) {
    val file = File(context.filesDir.path+"/internal_images")
    if (file.exists()) {
        file.delete()
    }
}