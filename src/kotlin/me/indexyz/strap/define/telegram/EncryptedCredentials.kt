package me.indexyz.strap.define.telegram

data class EncryptedCredentials (
    val data: String,
    val hash: String,
    val secret: String
)