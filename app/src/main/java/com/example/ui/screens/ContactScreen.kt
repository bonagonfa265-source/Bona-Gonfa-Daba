package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentHelper

@Composable
fun ContactScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inquiryMessage by remember { mutableStateOf("") }
    var inquiryName by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Branding Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Brush.linearGradient(listOf(DiamondCyan.copy(alpha = 0.5f), ElectricViolet.copy(alpha = 0.4f)))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "💎✨", fontSize = 28.sp)
                        Column {
                            Text(
                                text = "BONA G",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Design • Print • Order from Home",
                                style = MaterialTheme.typography.bodySmall,
                                color = RadiantGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "We are dedicated to bringing your creative ideas to life with high quality design and printing. Contact us directly through any channel below:",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Direct Contact Methods
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
                        text = "Official Contact Channels",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    // Telegram Channel
                    ContactMethodRow(
                        iconEmoji = "📲",
                        title = "Telegram",
                        subtitle = "@Bonagraphics",
                        buttonText = "Open Telegram",
                        buttonColor = TelegramBlue,
                        onClick = { IntentHelper.openTelegram(context) },
                        testTag = "contact_open_telegram"
                    )

                    // Phone 1 (0919199459)
                    ContactMethodRow(
                        iconEmoji = "📞",
                        title = "Phone / WhatsApp 1",
                        subtitle = "0919199459",
                        buttonText = "Call",
                        buttonColor = DiamondCyan,
                        onClick = { IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_PRIMARY) },
                        secondaryButtonText = "WhatsApp",
                        onSecondaryClick = {
                            IntentHelper.openWhatsApp(
                                context,
                                IntentHelper.PHONE_PRIMARY,
                                "Hello Bona Graphics! I would like to make an inquiry."
                            )
                        },
                        testTag = "contact_phone1"
                    )

                    // Phone 2 (0953019109)
                    ContactMethodRow(
                        iconEmoji = "📞",
                        title = "Phone / WhatsApp 2",
                        subtitle = "0953019109",
                        buttonText = "Call",
                        buttonColor = ElectricViolet,
                        onClick = { IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_SECONDARY) },
                        secondaryButtonText = "WhatsApp",
                        onSecondaryClick = {
                            IntentHelper.openWhatsApp(
                                context,
                                IntentHelper.PHONE_SECONDARY,
                                "Hello Bona Graphics! I would like to make an inquiry."
                            )
                        },
                        testTag = "contact_phone2"
                    )
                }
            }
        }

        // Instant Quick Message / Inquiry Form
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
                        text = "💬 Send a Quick Inquiry",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    OutlinedTextField(
                        value = inquiryName,
                        onValueChange = { inquiryName = it },
                        label = { Text("Your Name") },
                        placeholder = { Text("e.g. Sara or Team Lead") },
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
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = inquiryMessage,
                        onValueChange = { inquiryMessage = it },
                        label = { Text("What would you like to ask or design?") },
                        placeholder = { Text("e.g. Can you print 20 polo shirts for our church event by this Friday?") },
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
                            .testTag("inquiry_message_input")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val text = buildString {
                                    appendLine("💎 Bona Graphics Inquiry:")
                                    if (inquiryName.isNotBlank()) appendLine("From: $inquiryName")
                                    appendLine(inquiryMessage.ifBlank { "Hello! I want to inquire about custom design and printing." })
                                }
                                IntentHelper.openTelegram(context, text)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("inquiry_send_telegram")
                        ) {
                            Text(text = "Send via Telegram", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                val text = buildString {
                                    appendLine("💎 Bona Graphics Inquiry:")
                                    if (inquiryName.isNotBlank()) appendLine("From: $inquiryName")
                                    appendLine(inquiryMessage.ifBlank { "Hello! I want to inquire about custom design and printing." })
                                }
                                IntentHelper.openWhatsApp(context, IntentHelper.PHONE_PRIMARY, text)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("inquiry_send_whatsapp")
                        ) {
                            Text(text = "Send via WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkObsidian)
                        }
                    }
                }
            }
        }

        // FAQs and Business Information
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "ℹ️ How Ordering Works",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    FaqItem(
                        icon = Icons.Default.LocationOn,
                        title = "Convenient Service from Home",
                        desc = "You don't need to visit a printing shop in traffic. Submit your ideas, approve digital drafts on your phone, and have your items delivered straight to your door in Addis Ababa or shipped via regional transport."
                    )

                    FaqItem(
                        icon = Icons.Default.Schedule,
                        title = "Fast Turnaround Time",
                        desc = "Standard design & printing orders are completed within 24 to 48 hours. Urgent same-day express printing is also available upon confirmation."
                    )

                    FaqItem(
                        icon = Icons.Default.Payment,
                        title = "Convenient Ethiopian Payment Options",
                        desc = "We support Telebirr, Commercial Bank of Ethiopia (CBE), Awash Bank transfer, and Cash on delivery upon verified request."
                    )

                    FaqItem(
                        icon = Icons.Default.HelpOutline,
                        title = "Can I bring my own blank shirts or garments?",
                        desc = "Yes! You can provide your own garments for custom screen printing or embroidery, or select from our high quality premium cotton blanks."
                    )
                }
            }
        }
    }
}

@Composable
fun ContactMethodRow(
    iconEmoji: String,
    title: String,
    subtitle: String,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    testTag: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurface)
            .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = iconEmoji, fontSize = 22.sp)
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = DiamondCyan,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag(testTag)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (buttonColor == DiamondCyan) DarkObsidian else Color.White
                )
            }

            if (secondaryButtonText != null && onSecondaryClick != null) {
                Button(
                    onClick = onSecondaryClick,
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = secondaryButtonText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkObsidian
                    )
                }
            }
        }
    }
}

@Composable
fun FaqItem(
    icon: ImageVector,
    title: String,
    desc: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(DiamondCyan.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = DiamondCyan, modifier = Modifier.size(18.dp))
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}
