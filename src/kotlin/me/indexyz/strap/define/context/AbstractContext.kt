package me.indexyz.strap.define.context

import me.indexyz.strap.`object`.Update
import me.indexyz.strap.utils.Configuration
import me.indexyz.strap.utils.Network

open class AbstractContext(
        var network: Network,
        var update: Update,
        var configuration: Configuration
)