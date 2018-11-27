package me.indexyz.strap.define.telegram

data class PassportData (
    val data: List<EncryptedPassportElement>,
    val credentials: EncryptedCredentials
)