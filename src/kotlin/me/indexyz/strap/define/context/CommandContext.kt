package me.indexyz.strap.define.context

import me.indexyz.strap.`object`.Update
import me.indexyz.strap.utils.Configuration
import me.indexyz.strap.utils.Network
import java.util.*

class CommandContext(
        network: Network,
        update: Update,
        configuration: Configuration,
        var args: Optional<List<String>>
): MessageContext(
    network, update, configuration
)