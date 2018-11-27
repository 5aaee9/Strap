package me.indexyz.strap.define.telegram

data class EncryptedPassportElement (
    val type: String,
    val data: String?,
    val phone_number: String?,
    val email: String?,
    val files: List<PassportFile>?,
    val front_side: PassportFile?,
    val reverse_side: PassportFile?,
    val selfie: PassportFile?,
    val translation: List<PassportFile>?,
    val hash: String
)