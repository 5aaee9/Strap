package me.indexyz.strap.objects

data class User (
        val id: Long = 0,
        val is_bot: Boolean,
        val first_name: String,
        val last_name: String? = null,
        val username: String? = null,
        val language_code: String? = null
)