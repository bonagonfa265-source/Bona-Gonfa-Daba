package com.example

import com.example.data.model.ServiceCatalog
import com.example.util.IntentHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun verify_bona_graphics_service_pricing() {
        val tshirt = ServiceCatalog.getServiceById("tshirt")
        assertNotNull(tshirt)
        assertEquals(500, tshirt?.priceEtb)

        val banner = ServiceCatalog.getServiceById("banner")
        assertNotNull(banner)
        assertEquals(600, banner?.priceEtb)

        val sticker = ServiceCatalog.getServiceById("sticker")
        assertNotNull(sticker)
        assertEquals(650, sticker?.priceEtb)

        val poster = ServiceCatalog.getServiceById("poster")
        assertNotNull(poster)
        assertEquals(450, poster?.priceEtb)

        val social = ServiceCatalog.getServiceById("social")
        assertNotNull(social)
        assertEquals(400, social?.priceEtb)

        val certificate = ServiceCatalog.getServiceById("certificate")
        assertNotNull(certificate)
        assertEquals(350, certificate?.priceEtb)
    }

    @Test
    fun verify_order_message_formatting() {
        val msg = IntentHelper.formatOrderMessage(
            orderNumber = "1234",
            serviceTitle = "Custom T-Shirt",
            customerName = "Bona",
            customerPhone = "0919199459",
            customerAddress = "Addis Ababa",
            telegramUsername = "@bonagraphics",
            selectedColor = "Obsidian Black",
            selectedSize = "L",
            selectedOption = "Front Chest Print",
            customText = "Bona Graphics 💎🖤",
            quantity = 2,
            totalPriceEtb = 1000,
            notes = "Handle with care"
        )

        assertTrue(msg.contains("#1234"))
        assertTrue(msg.contains("Custom T-Shirt"))
        assertTrue(msg.contains("1000 ETB"))
        assertTrue(msg.contains("0919199459"))
        assertTrue(msg.contains("BONA G"))
        assertTrue(msg.contains("Order from Home"))
    }
}
