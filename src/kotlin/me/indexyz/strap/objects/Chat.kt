package me.indexyz.strap.objects

data class Chat(
        val id: Long = 0,
        val type: String,
        val title: String? = null,
        val username: String? = null,
        val first_name: String,
        val last_name: String? = null,

        val all_members_are_administrators: Boolean = false,
        val description: String? = null,
        val invite_link: String? = null,
        val pinned_message: Message? = null,
        val sticker_set_name: String? = null,
        val can_set_sticker_set: Boolean = false
)