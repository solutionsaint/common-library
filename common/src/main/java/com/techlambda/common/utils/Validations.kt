package com.techlambda.common.utils

import android.util.Patterns
import java.util.regex.Pattern

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneNumberPattern = "^[+]?[0-9]{10,13}\$"
    return Pattern.matches(phoneNumberPattern, phoneNumber)
}

fun isPhoneNumber(input: String): Boolean {
    val numericPattern = "^[0-9]+$"
    return input.matches(Regex(numericPattern))
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return Pattern.matches(emailPattern, email)
}

fun isValidUrl(url: String): Boolean {
    if (!Patterns.WEB_URL.matcher(url).matches()) {
        return false
    }
    val urlPattern = "^https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*$".toRegex()
    return urlPattern.matches(url)
}