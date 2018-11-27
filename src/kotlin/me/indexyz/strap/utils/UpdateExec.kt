package me.indexyz.strap.utils

import me.indexyz.strap.Annotations
import me.indexyz.strap.`object`.Update
import me.indexyz.strap.define.*
import me.indexyz.strap.define.context.AbstractContext
import me.indexyz.strap.define.context.CommandContext
import me.indexyz.strap.define.context.MessageContext
import me.indexyz.strap.define.context.UserEventContext
import java.lang.reflect.InvocationTargetException
import java.util.*

public class UpdateExec(private val network: Network, private val session: Session) {
    private val classCache = findAnnotations(Annotations.Events::class.java)
    private val configuration: Configuration

    init {
        this.configuration = Configuration.get()
        this.onInit()
    }

    fun onInit() {
        findMethods(classCache, Annotations.OnInit::class.java).forEach { it ->
            try {
                it.invoke(null, this.network)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
    }

    private fun createContext(update: Update): AbstractContext {
        val context = AbstractContext(network, update, this.configuration)

        return context
    }

    fun execCommandUpdate(update: Update) {
        val methods = findMethods(classCache, Annotations.Message.Command::class.java)

        val context: CommandContext = CommandContext(network = network,
                update = update, configuration = configuration,
                args = Optional.empty())

        methods.forEach {
            try {
                val command = it.getAnnotation(Annotations.Message.Command::class.java)


                // not response in group
                if (!command.responseInGroup && update.message.chat!!.type != ChatType.PRIVATE.type) {
                    return
                }

                if (update.message.text!!.startsWith("/" + command.command)) {
                    if (command.parseArgs) {
                        val args = Arrays.asList(*update.message.text!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

                        if (args.size > 1) {
                            context.args = Optional.of(args.subList(1, args.size))
                        }
                    }

                    it.invoke(null, context)
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    fun execUserEvent(update: Update, kind: UserEventsKind) {
        val context = UserEventContext(
                network, update, configuration
        )

        val methods = findMethods(classCache, Annotations.Message.UserEvents::class.java)
        methods.forEach {
            try {
                val command = it.getAnnotation(Annotations.Message.UserEvents::class.java)

                if (command.kind === kind) {
                    it.invoke(null, context)
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
    }

    fun execMessageEvent(update: Update) {
        val context = MessageContext(network, update, configuration)

        val methods = findMethods(classCache, Annotations.Message::class.java)

        for (method in methods) {
            try {
                method.invoke(null, context)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
    }
}