package me.indexyz.strap.define.context

import me.indexyz.strap.`object`.Update
import me.indexyz.strap.utils.Configuration
import me.indexyz.strap.utils.Network

open class MessageContext(
    network: Network,
    update: Update,
    configuration: Configuration
) : AbstractContext(
    network, update , configuration
) {
    fun reply(message: String) {
        this.network.sendMessage(
                this.update.message.chat!!.id,
                message,
                "Markdown",
                false,
                false,
                this.update.message.message_id)
    }
}