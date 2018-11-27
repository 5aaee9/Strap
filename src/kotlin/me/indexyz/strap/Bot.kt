package me.indexyz.strap

import me.indexyz.strap.define.Session
import me.indexyz.strap.define.UserEventsKind
import me.indexyz.strap.define.telegram.Update
import me.indexyz.strap.exceptions.UpdateFailure
import me.indexyz.strap.utils.Network
import me.indexyz.strap.utils.UpdateExec
import org.apache.logging.log4j.LogManager

class Bot {
    companion object {
        private var instance: Bot? = null
            get() {
                if (field == null) {
                    field = Bot()
                }
                return field
            }
        fun get(): Bot {
            return instance!!
        }

        fun create(token: String, session: Session): Bot {
            Bot.instance = Bot()
            Bot.instance!!.init(token, session)
            return get()
        }

        val logger = LogManager.getLogger(Bot::class.java)!!
    }

    private var network: Network? = null
    private var enabled: Boolean = false
    private var instance: Bot? = null
    private var execer: UpdateExec? = null

    fun getNetwork(): Network? {
        return network
    }

    fun init(token: String, session: Session) {
        this.network = Network(token)
        this.execer = UpdateExec(this.network!!, session)
    }


    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun start() {
        // Start pulling message
        this.enabled = true
        var lastUpdateId: Long = 0

        while (this.enabled) {
            try {
                val updates = this.network!!.getUpdates(lastUpdateId)
                if (updates == null) {
                    logger.error("Unable to get updates, check your token.")
                    Thread.sleep(1000)
                    continue
                }

                if (updates.size != 0) {
                    lastUpdateId = updates.get(updates.size - 1).update_id + 1
                }

                updates.stream()
                        .forEach { update -> Thread { this.execUpdate(update) }.start() }
                Thread.sleep(1000)
            } catch (e: UpdateFailure) {
                logger.info("update failure, check your bot token")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun execUpdate(update: Update) {
        if (update.message?.text != null) if (update.message.text.startsWith("/")) {
            this.execer!!.execCommandUpdate(update)
        } else {
            this.execer!!.execMessageEvent(update)
        }

        if (update.message?.new_chat_members != null) {
            this.execer!!.execUserEvent(update, UserEventsKind.JOIN_CHAT)
        }
    }
}