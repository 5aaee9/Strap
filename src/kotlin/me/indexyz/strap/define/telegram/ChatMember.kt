package me.indexyz.strap.define.telegram

data class ChatMember (
    val user: User,
    val status: String,
    val until_date: Long?,
    val can_be_edited: Boolean?,
    val can_change_info: Boolean?,
    val can_post_messages: Boolean?,
    val can_edit_messages: Boolean?,
    val can_delete_messages: Boolean?,
    val can_invite_users: Boolean?,
    val can_restrict_members: Boolean?,
    val can_pin_messages: Boolean?,
    val can_promote_members: Boolean?,
    val can_send_messages: Boolean?,
    val can_send_media_messages: Boolean?,
    val can_send_other_messages: Boolean?,
    val can_add_web_page_previews: Boolean?
)