package me.indexyz.strap.utils

import com.google.common.collect.Lists
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.indexyz.strap.Bot
import me.indexyz.strap.`object`.Update
import me.indexyz.strap.exceptions.UpdateFailure
import me.indexyz.strap.objects.Message
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.ArrayList

class Network(private val token: String) {

    fun getRequestUrl(path: String): String {
        val builder = StringBuilder()
        builder.append("https://api.telegram.org/bot")
                .append(this.token)
                .append(path)
        return builder.toString()
    }

    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val client = OkHttpClient()

    private fun sendReq(path: String, body: RequestBody): JSONObject {
        return sendReq(path, body, 0)
    }

    private fun sendReq(path: String, body: RequestBody, depth: Int): JSONObject {
        val request = Request.Builder()
                .url(getRequestUrl(path))
                .header("Content-Type", "application/json")
                .post(body)
                .build()

        try {
            if (depth < 5) {
                val response = client.newCall(request).execute()
                assert(response.body() != null)
                val respBody = response.body()!!.string()

                response.close()
                val `object` = JSONObject(respBody)

                if (!`object`.getBoolean("ok") && `object`.has("result")) {
                    Bot.logger.info(respBody)
                    return sendReq(path, body, depth + 1)
                }

                return `object`
            } else {
                throw UpdateFailure()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw UpdateFailure()
        }

    }

    fun getUpdates(id: Long): ArrayList<Update>? {
        val resp = sendReq("/getUpdates", createFromJson(JSONObject().put("offset", id)))

        val returnList = Lists.newArrayList<Update>()
        val adapter = moshi.adapter(Update::class.java)

        resp.getJSONArray("result").forEach {
            item -> returnList.add(adapter.fromJson(item.toString()))
        }

        return returnList
    }

    val JSON = MediaType.parse("application/json; charset=utf-8")

    private fun createFromJson(json: JSONObject): RequestBody {
        return RequestBody.create(JSON, json.toString())
    }

    fun <T> sendMessage(
            chatId: T,
            text: String,
            parseMode: String = "Markdown",
            disableWebPagePreview: Boolean = false,
            disableNotification: Boolean = false,
            replyToMessage_id: Long? = null
    ): Message {

        val `object` = JSONObject()
        `object`.put("chat_id", chatId)
        `object`.put("text", text)
        `object`.put("parse_mode", parseMode)
        `object`.put("disable_web_page_preview", disableWebPagePreview)
        `object`.put("disable_notification", disableNotification)
        `object`.put("reply_to_message_id", replyToMessage_id)

        val resp = sendReq("/sendMessage", createFromJson(`object`))
        return moshi.adapter(Message::class.java)
                .fromJson(resp.getJSONObject("result").toString())!!
    }

    fun <T> kickChatMember(
            chatId: T,
            userId: Long,
            untilDate: Long = 0
    ) {
        val `object` = JSONObject()
        `object`.put("chat_id", chatId)
        `object`.put("user_id", userId)
        `object`.put("until_date", untilDate)

        sendReq("/kickChatMember", createFromJson(`object`))
    }

    fun <T> unbanChatMember(chatId: T, userId: Long) {
        val `object` = JSONObject()
        `object`.put("chat_id", chatId)
        `object`.put("user_id", userId)

        sendReq("/unbanChatMember", createFromJson(`object`))
    }

    private fun detectType(path: Path): String {
        try {
            return Files.probeContentType(path)
        } catch (e: IOException) {
            e.printStackTrace()

            return "application/octet-stream"
        }

    }

}