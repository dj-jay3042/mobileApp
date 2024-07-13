package com.dj_jay.smsscrapper

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

class SendSmsTask : AsyncTask<Map<String, String?>, Void, Void>() {

    override fun doInBackground(vararg params: Map<String, String?>): Void? {
        try {
            val url = URL("https://admin.dj-jay.in/api/storeSms")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; utf-8")
            conn.setRequestProperty("Accept", "application/json")
            conn.doOutput = true

            val jsonInputString = "{\"phone\": \"${params[0]["phone"]}\", \"message\": \"${params[0]["message"]}\"}"

            conn.outputStream.use { os ->
                val input = jsonInputString.toByteArray(charset("utf-8"))
                os.write(input, 0, input.size)
            }

            val code = conn.responseCode
            println("Response Code: $code")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}