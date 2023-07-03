package com.goskate.goskate.ui.components

fun String.isValidEmail(): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return matches(emailPattern.toRegex())
}
