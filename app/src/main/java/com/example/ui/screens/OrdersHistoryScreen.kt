package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.ui.AppScreen
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningOrange
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersHistoryScreen(
    orders: List<OrderEntity>,
    filter: String,
    onFilterChange: (String) -> Unit,
    selectedOrder: OrderEntity?,
    onSelectOrder: (OrderEntity?) -> Unit,
    onDeleteOrder: (Long) -> Unit,
    onUpdateStatus: (OrderEntity, String) -> Unit,
    onNavigateToBuilder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val filteredOrders = remember(orders, filter) {
        when (filter) {
            "ACTIVE" -> orders.filter { it.status != "Completed" }
            "COMPLETED" -> orders.filter { it.status == "Completed" }
            else -> orders
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "📦 My Orders & Receipts",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Track your custom design and print orders locally, view receipts, and communicate with Bona Graphics.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Filter Pills
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val filters = listOf("ALL" to "All Orders", "ACTIVE" to "In Progress", "COMPLETED" to "Completed")
                filters.forEach { (key, label) ->
                    val isSelected = filter == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { onFilterChange(key) },
                        label = { Text(text = label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DiamondCyan.copy(alpha = 0.2f),
                            selectedLabelColor = DiamondCyan,
                            containerColor = DarkSurfaceElevated,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = DarkCardBorder,
                            selectedBorderColor = DiamondCyan
                        )
                    )
                }
            }
        }

        if (filteredOrders.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "🎨", fontSize = 42.sp)
                        Text(
                            text = "No orders found in this category",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Place your first custom T-shirt, banner, sticker, or logo order and track it right here.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Button(
                            onClick = onNavigateToBuilder,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DiamondCyan,
                                contentColor = DarkObsidian
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Start an Order ✨", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            items(filteredOrders, key = { it.id }) { order ->
                OrderHistoryCard(
                    order = order,
                    onViewReceipt = { onSelectOrder(order) },
                    onDelete = { onDeleteOrder(order.id) }
                )
            }
        }
    }

    // Receipt Modal
    if (selectedOrder != null) {
        DigitalReceiptDialog(
            order = selectedOrder,
            onDismiss = { onSelectOrder(null) },
            onUpdateStatus = { newStatus -> onUpdateStatus(selectedOrder, newStatus) },
            onDelete = {
                onDeleteOrder(selectedOrder.id)
                onSelectOrder(null)
            }
        )
    }
}

@Composable
fun OrderHistoryCard(
    order: OrderEntity,
    onViewReceipt: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateString = remember(order.createdAtMillis) {
        val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
        sdf.format(Date(order.createdAtMillis))
    }

    val (statusBg, statusFg) = when (order.status) {
        "Order Placed" -> InfoBlue.copy(alpha = 0.2f) to InfoBlue
        "In Design" -> ElectricViolet.copy(alpha = 0.2f) to ElectricViolet
        "In Printing" -> WarningOrange.copy(alpha = 0.2f) to WarningOrange
        "Ready for Delivery" -> SuccessGreen.copy(alpha = 0.2f) to SuccessGreen
        "Completed" -> DiamondCyan.copy(alpha = 0.2f) to DiamondCyan
        else -> DarkSurface to TextSecondary
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewReceipt() }
            .testTag("order_card_${order.orderNumber}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row: Order Number & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "#${order.orderNumber}",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = statusBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusFg.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = order.status,
                        color = statusFg,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Title & Customer Details
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = order.serviceTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DiamondCyan
                )
                Text(
                    text = "Customer: ${order.customerName} (${order.customerPhone})",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            // Specifications Pill summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (order.selectedColor.isNotBlank()) {
                    SpecChip(text = "🎨 ${order.selectedColor}")
                }
                if (order.selectedSize.isNotBlank()) {
                    SpecChip(text = "📏 ${order.selectedSize}")
                }
                SpecChip(text = "Qty: ${order.quantity}")
            }

            // Price and Date Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Amount",
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                    Text(
                        text = "${order.totalPriceEtb} ETB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RadiantGold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onViewReceipt,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = DiamondCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = "Slip", color = DiamondCyan, fontSize = 12.sp)
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpecChip(text: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = DarkSurface,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, DarkCardBorder)
    ) {
        Text(
            text = text,
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun DigitalReceiptDialog(
    order: OrderEntity,
    onDismiss: () -> Unit,
    onUpdateStatus: (String) -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val formattedMessage = remember(order) {
        IntentHelper.formatOrderMessage(
            orderNumber = order.orderNumber,
            serviceTitle = order.serviceTitle,
            customerName = order.customerName,
            customerPhone = order.customerPhone,
            customerAddress = order.customerAddress,
            telegramUsername = order.telegramUsername,
            selectedColor = order.selectedColor,
            selectedSize = order.selectedSize,
            selectedOption = order.selectedOption,
            customText = order.customText,
            quantity = order.quantity,
            totalPriceEtb = order.totalPriceEtb,
            notes = order.notes
        )
    }

    val statuses = listOf("Order Placed", "In Design", "In Printing", "Ready for Delivery", "Completed")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurfaceElevated,
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Official Order Slip",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Bona Graphics #BG-${order.orderNumber}",
                        color = DiamondCyan,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Status Stepper / Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Track / Update Production Status:",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(statuses) { st ->
                                val isSelected = st == order.status
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) DiamondCyan.copy(alpha = 0.25f) else DarkSurface,
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) DiamondCyan else DarkCardBorder
                                    ),
                                    modifier = Modifier.clickable { onUpdateStatus(st) }
                                ) {
                                    Text(
                                        text = st,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) DiamondCyan else TextSecondary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    // Digital Receipt Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(DarkSurface)
                            .border(1.dp, DarkCardBorder, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "💎 BONA GRAPHICS RECEIPT",
                                fontWeight = FontWeight.Bold,
                                color = RadiantGold,
                                fontSize = 13.sp
                            )
                            Text(text = "Service: ${order.serviceTitle}", color = TextPrimary, fontSize = 12.sp)
                            Text(text = "Quantity: ${order.quantity}", color = TextSecondary, fontSize = 12.sp)
                            if (order.selectedColor.isNotBlank()) Text(text = "Color/Finish: ${order.selectedColor}", color = TextSecondary, fontSize = 12.sp)
                            if (order.selectedSize.isNotBlank()) Text(text = "Size: ${order.selectedSize}", color = TextSecondary, fontSize = 12.sp)
                            if (order.selectedOption.isNotBlank()) Text(text = "Style: ${order.selectedOption}", color = TextSecondary, fontSize = 12.sp)
                            if (order.customText.isNotBlank()) Text(text = "Custom Text: \"${order.customText}\"", color = DiamondCyan, fontSize = 12.sp)
                            Text(text = "Customer: ${order.customerName}", color = TextSecondary, fontSize = 12.sp)
                            Text(text = "Phone: ${order.customerPhone}", color = TextSecondary, fontSize = 12.sp)
                            if (order.customerAddress.isNotBlank()) Text(text = "Address: ${order.customerAddress}", color = TextSecondary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "TOTAL: ${order.totalPriceEtb} ETB",
                                fontWeight = FontWeight.ExtraBold,
                                color = RadiantGold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                // Quick Send to Telegram
                item {
                    Button(
                        onClick = { IntentHelper.openTelegram(context, formattedMessage) },
                        colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Send Slip via Telegram (@Bonagraphics)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Quick Send to WhatsApp
                item {
                    Button(
                        onClick = { IntentHelper.openWhatsApp(context, IntentHelper.PHONE_PRIMARY, formattedMessage) },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "💬 Send Slip via WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkObsidian)
                    }
                }

                // Copy Text
                item {
                    OutlinedButton(
                        onClick = { IntentHelper.copyToClipboard(context, "Bona Graphics Slip", formattedMessage) },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Copy Receipt Text", fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = DiamondCyan, contentColor = DarkObsidian),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Close", fontWeight = FontWeight.Bold)
            }
        }
    )
}
