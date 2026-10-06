package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object IntentHelper {

    const val TELEGRAM_USERNAME = "Bonagraphics"
    const val PHONE_PRIMARY = "0919199459"
    const val PHONE_SECONDARY = "0953019109"

    fun openTelegram(context: Context, prefilledMessage: String? = null) {
        val encodedMessage = if (!prefilledMessage.isNullOrBlank()) {
            URLEncoder.encode(prefilledMessage, StandardCharsets.UTF_8.toString())
        } else null

        val uri = if (encodedMessage != null) {
            Uri.parse("https://t.me/$TELEGRAM_USERNAME?text=$encodedMessage")
        } else {
            Uri.parse("https://t.me/$TELEGRAM_USERNAME")
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open Telegram. Opening browser...", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, phoneNumber: String = PHONE_PRIMARY, message: String) {
        // Ethiopian international format: replace leading 0 with 251
        val formattedNumber = if (phoneNumber.startsWith("0")) {
            "251" + phoneNumber.substring(1)
        } else {
            phoneNumber
        }
        val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
        val url = "https://wa.me/$formattedNumber?text=$encodedMessage"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }

    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open phone dialer", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    fun shareText(context: Context, title: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, "Share via")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    fun formatOrderMessage(
        orderNumber: String,
        serviceTitle: String,
        customerName: String,
        customerPhone: String,
        customerAddress: String,
        telegramUsername: String,
        selectedColor: String,
        selectedSize: String,
        selectedOption: String,
        customText: String,
        quantity: Int,
        totalPriceEtb: Int,
        notes: String
    ): String {
        return buildString {
            appendLine("💎✨ BONA G — ORDER FROM HOME")
            appendLine("━━━━━━━━━━━━━━━━━━━━")
            appendLine("📋 Order ID: #$orderNumber")
            appendLine("🎨 Service: $serviceTitle")
            appendLine("🔢 Quantity: $quantity")
            if (selectedColor.isNotBlank()) appendLine("🎨 Color/Finish: $selectedColor")
            if (selectedSize.isNotBlank()) appendLine("📏 Size/Spec: $selectedSize")
            if (selectedOption.isNotBlank()) appendLine("⚙️ Style/Placement: $selectedOption")
            if (customText.isNotBlank()) appendLine("✍️ Custom Text: \"$customText\"")
            appendLine("💰 Total: $totalPriceEtb ETB")
            appendLine("━━━━━━━━━━━━━━━━━━━━")
            appendLine("👤 Customer: $customerName")
            appendLine("📞 Phone: $customerPhone")
            if (telegramUsername.isNotBlank()) appendLine("📲 Telegram: $telegramUsername")
            if (customerAddress.isNotBlank()) appendLine("📍 Delivery City/Address: $customerAddress")
            if (notes.isNotBlank()) appendLine("📝 Instructions: $notes")
            appendLine("━━━━━━━━━━━━━━━━━━━━")
            appendLine("Design • Print • Order from Home 🏠💎")
            appendLine("Your Idea. Our Design. Your Style. 💎🖤")
        }
    }
}
