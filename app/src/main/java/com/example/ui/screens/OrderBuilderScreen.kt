package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.data.model.ServiceCatalog
import com.example.ui.OrderFormState
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentHelper

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderBuilderScreen(
    formState: OrderFormState,
    totalEtb: Int,
    onServiceSelected: (String) -> Unit,
    onCustomerNameChange: (String) -> Unit,
    onCustomerPhoneChange: (String) -> Unit,
    onCustomerAddressChange: (String) -> Unit,
    onTelegramUsernameChange: (String) -> Unit,
    onColorSelected: (String) -> Unit,
    onSizeSelected: (String) -> Unit,
    onOptionSelected: (String) -> Unit,
    onCustomTextChange: (String) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onNotesChange: (String) -> Unit,
    onSubmitOrder: () -> Unit,
    activeOrderModal: OrderEntity?,
    onDismissModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentService = ServiceCatalog.getServiceById(formState.selectedServiceId)
        ?: ServiceCatalog.services.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "✨ Custom Order Builder",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Configure your items, get live ETB prices, and send directly to Bona Graphics.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Error Banner if validation failed
        if (formState.errorMessage != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEF4444).copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⚠️ ${formState.errorMessage}",
                        color = Color(0xFFFCA5A5),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Step 1: Select Service
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "1. Choose Service",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 15.sp
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ServiceCatalog.services) { service ->
                            val isSelected = service.id == formState.selectedServiceId
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) DiamondCyan.copy(alpha = 0.2f) else DarkSurface,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) DiamondCyan else DarkCardBorder
                                ),
                                modifier = Modifier
                                    .clickable { onServiceSelected(service.id) }
                                    .testTag("select_service_${service.id}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(text = service.iconEmoji, fontSize = 16.sp)
                                    Text(
                                        text = service.title,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) DiamondCyan else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Step 2: Customization Options
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "2. Customize Your ${currentService.title}",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 15.sp
                    )

                    // Color / Finish Selection
                    if (currentService.availableColors.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Color / Finish:",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                currentService.availableColors.forEach { color ->
                                    val isSelected = color == formState.selectedColor
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) DiamondCyan.copy(alpha = 0.25f) else DarkSurface,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) DiamondCyan else DarkCardBorder
                                        ),
                                        modifier = Modifier.clickable { onColorSelected(color) }
                                    ) {
                                        Text(
                                            text = color,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) DiamondCyan else TextSecondary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Size / Format Selection
                    if (currentService.availableSizes.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Size / Format:",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                currentService.availableSizes.forEach { size ->
                                    val isSelected = size == formState.selectedSize
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) DiamondCyan.copy(alpha = 0.25f) else DarkSurface,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) DiamondCyan else DarkCardBorder
                                        ),
                                        modifier = Modifier.clickable { onSizeSelected(size) }
                                    ) {
                                        Text(
                                            text = size,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) DiamondCyan else TextSecondary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Placement / Addon Options
                    if (currentService.availableOptions.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Style / Placement / Addons:",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                currentService.availableOptions.forEach { opt ->
                                    val isSelected = opt == formState.selectedOption
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) ElectricViolet.copy(alpha = 0.25f) else DarkSurface,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) ElectricViolet else DarkCardBorder
                                        ),
                                        modifier = Modifier.clickable { onOptionSelected(opt) }
                                    ) {
                                        Text(
                                            text = opt,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) ElectricViolet else TextSecondary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Custom Text / Slogan
                    OutlinedTextField(
                        value = formState.customText,
                        onValueChange = onCustomTextChange,
                        label = { Text("Custom Text or Slogan to Print/Design") },
                        placeholder = { Text("e.g. Happy 25th Birthday Abebe / Bona FC") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("builder_custom_text_input")
                    )

                    // Quantity Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Quantity:",
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Units / Items needed",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            IconButton(
                                onClick = { onQuantityChange(formState.quantity - 1) },
                                enabled = formState.quantity > 1,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(DarkSurface)
                                    .border(1.dp, DarkCardBorder, CircleShape)
                                    .testTag("qty_minus_btn")
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextPrimary)
                            }

                            Text(
                                text = "${formState.quantity}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DiamondCyan
                            )

                            IconButton(
                                onClick = { onQuantityChange(formState.quantity + 1) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(DarkSurface)
                                    .border(1.dp, DarkCardBorder, CircleShape)
                                    .testTag("qty_plus_btn")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", tint = TextPrimary)
                            }
                        }
                    }
                }
            }
        }

        // Step 3: Customer Information
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "3. Customer & Delivery Information",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 15.sp
                    )

                    OutlinedTextField(
                        value = formState.customerName,
                        onValueChange = onCustomerNameChange,
                        label = { Text("Your Full Name *") },
                        placeholder = { Text("e.g. Bona Gonfa") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_name_input")
                    )

                    OutlinedTextField(
                        value = formState.customerPhone,
                        onValueChange = onCustomerPhoneChange,
                        label = { Text("Phone Number (09...) *") },
                        placeholder = { Text("0919199459") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_phone_input")
                    )

                    OutlinedTextField(
                        value = formState.customerAddress,
                        onValueChange = onCustomerAddressChange,
                        label = { Text("City / Sub-city / Delivery Address") },
                        placeholder = { Text("e.g. Addis Ababa, Bole Medhanialem") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_address_input")
                    )

                    OutlinedTextField(
                        value = formState.telegramUsername,
                        onValueChange = onTelegramUsernameChange,
                        label = { Text("Telegram Username (Optional)") },
                        placeholder = { Text("@username") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_telegram_input")
                    )

                    OutlinedTextField(
                        value = formState.notes,
                        onValueChange = onNotesChange,
                        label = { Text("Special Design Instructions / Notes") },
                        placeholder = { Text("Tell us your font style, preferred logo placement, deadline...") },
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DiamondCyan,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_notes_input")
                    )
                }
            }
        }

        // Live Price & Submit Bar
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DiamondCyan.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Estimated Total Price:",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                            Text(
                                text = "${totalEtb} ETB",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = RadiantGold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = DiamondCyan.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${formState.quantity} item(s)",
                                color = DiamondCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    Button(
                        onClick = onSubmitOrder,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DiamondCyan,
                            contentColor = DarkObsidian
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("submit_order_btn")
                    ) {
                        Text(
                            text = "Submit Order & Connect With Us 💎",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Text(
                        text = "🔒 No advance charge inside the app. You verify the design draft on Telegram/WhatsApp first before payment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }

    // Modal when order is placed
    if (activeOrderModal != null) {
        OrderSuccessDispatchModal(
            order = activeOrderModal,
            onDismiss = onDismissModal
        )
    }
}

@Composable
fun OrderSuccessDispatchModal(
    order: OrderEntity,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val formattedMessage = IntentHelper.formatOrderMessage(
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "🎉", fontSize = 24.sp)
                    Column {
                        Text(
                            text = "Order Saved!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "#${order.orderNumber}",
                            color = DiamondCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Your order has been recorded! To start your design or printing immediately, send the details to our designers via Telegram or WhatsApp below:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )

                // Dispatch to Telegram
                Button(
                    onClick = {
                        IntentHelper.openTelegram(context, formattedMessage)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dispatch_telegram_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Send via Telegram (@Bonagraphics)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // Dispatch to WhatsApp
                Button(
                    onClick = {
                        IntentHelper.openWhatsApp(context, IntentHelper.PHONE_PRIMARY, formattedMessage)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dispatch_whatsapp_btn")
                ) {
                    Text(
                        text = "💬 Send via WhatsApp (0919199459)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkObsidian
                    )
                }

                // Direct Call
                OutlinedButton(
                    onClick = {
                        IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_PRIMARY)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = DiamondCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Call Directly (0919199459)",
                        fontSize = 13.sp
                    )
                }

                // Copy summary
                OutlinedButton(
                    onClick = {
                        IntentHelper.copyToClipboard(context, "Bona Graphics Order", formattedMessage)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Copy Order Slip", fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Done / View in My Orders", color = DiamondCyan)
            }
        }
    )
}
