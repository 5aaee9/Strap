package me.indexyz.strap.objects

data class Message(
        val message_id: Long = 0,

        val from: User? = null,
        val chat: Chat? = null,

        val date: Long = 0,
        val text: String? = null,

        val forward_from: User? = null,
        val forward_from_chat: Chat? = null,
        val forward_from_message_id: Int = 0,
        val forward_signature: String? = null,
        val forward_date: Int = 0,
        val reply_to_message: Message? = null,
        val edit_date: Int = 0,
        val media_group_id: String? = null,
        val author_signature: String? = null,

        val new_chat_members: List<User>? = null
)