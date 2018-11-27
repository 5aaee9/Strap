package me.indexyz.strap.define.context

import me.indexyz.strap.`object`.Update
import me.indexyz.strap.utils.Configuration
import me.indexyz.strap.utils.Network

class UserEventContext(
    network: Network,
    update: Update,
    configuration: Configuration
): AbstractContext(
    network, update, configuration
)