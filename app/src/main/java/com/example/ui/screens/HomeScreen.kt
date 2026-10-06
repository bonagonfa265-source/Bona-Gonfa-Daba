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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceCatalog
import com.example.data.model.ServiceItem
import com.example.ui.AppScreen
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
fun HomeScreen(
    onNavigate: (AppScreen) -> Unit,
    onSelectService: (ServiceItem) -> Unit,
    onStartOrder: (ServiceItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(DiamondCyan.copy(alpha = 0.6f), ElectricViolet.copy(alpha = 0.3f))
                        ),
                        RoundedCornerShape(24.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    DiamondCyan.copy(alpha = 0.12f),
                                    Color.Transparent
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(DiamondCyan.copy(alpha = 0.15f))
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(text = "💎✨", fontSize = 14.sp)
                            Text(
                                text = "DESIGN • PRINT • ORDER FROM HOME",
                                color = DiamondCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = "Bona G 💎✨",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )

                        Text(
                            text = "Design • Print • Order from Home",
                            style = MaterialTheme.typography.titleMedium,
                            color = RadiantGold,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Your Idea. Our Design. Your Style. 💎🖤\nWelcome to Bona G! Your all-in-one studio for creative design, printing, and custom orders. Order from the comfort of your home and get your designs made with quality and care.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            lineHeight = 20.sp
                        )

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onNavigate(AppScreen.BUILDER) },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DiamondCyan,
                                    contentColor = DarkObsidian
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("hero_order_now_btn")
                            ) {
                                Text(
                                    text = "Order Now ✨",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { onNavigate(AppScreen.MOCKUP) },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = TextPrimary
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("hero_mockup_btn")
                            ) {
                                Text(
                                    text = "Mockup Studio 👕",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Direct Connect & Instant Messaging Banner
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Direct Contact & Fast Ordering",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Online Now 🟢",
                            fontSize = 11.sp,
                            color = SuccessGreen
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Telegram
                        Button(
                            onClick = { IntentHelper.openTelegram(context) },
                            colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_telegram_action")
                        ) {
                            Text(
                                text = "📲 @Bonagraphics",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Call 1
                        Button(
                            onClick = { IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_PRIMARY) },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, DiamondCyan.copy(alpha = 0.5f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_call1_action")
                        ) {
                            Text(
                                text = "📞 0919199459",
                                fontSize = 12.sp,
                                color = DiamondCyan,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // WhatsApp
                        Button(
                            onClick = {
                                IntentHelper.openWhatsApp(
                                    context,
                                    IntentHelper.PHONE_PRIMARY,
                                    "Hello Bona Graphics! I want to inquire about design and printing services."
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_whatsapp_action")
                        ) {
                            Text(
                                text = "💬 WhatsApp Us",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkObsidian
                            )
                        }

                        // Call 2
                        Button(
                            onClick = { IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_SECONDARY) },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ElectricViolet.copy(alpha = 0.5f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_call2_action")
                        ) {
                            Text(
                                text = "📞 0953019109",
                                fontSize = 12.sp,
                                color = ElectricViolet,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Order from Home 3-Step Feature Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Brush.horizontalGradient(listOf(DiamondCyan.copy(alpha = 0.4f), RadiantGold.copy(alpha = 0.4f)))
                ),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🏠", fontSize = 22.sp)
                            Column {
                                Text(
                                    text = "Order from Home in 3 Easy Steps",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Convenient • Safe • Fast Delivery",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DiamondCyan,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    val steps = listOf(
                        Triple("1️⃣", "Design & Choose", "Pick custom T-shirts, banners, stickers, or logos and customize text & colors in our Live Studio."),
                        Triple("2️⃣", "Direct 1-Tap Connect", "Send your order directly to Telegram @Bonagraphics or WhatsApp with one tap."),
                        Triple("3️⃣", "Fast Doorstep Delivery", "Approve your draft from your couch; we print with high quality and deliver straight to you!")
                    )

                    steps.forEach { (emoji, title, desc) ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = emoji, fontSize = 18.sp, modifier = Modifier.padding(top = 2.dp))
                            Column {
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
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { onNavigate(AppScreen.BUILDER) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RadiantGold,
                            contentColor = DarkObsidian
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("order_from_home_cta")
                    ) {
                        Text(
                            text = "Start Order from Home Now 🏠✨",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Services & Prices Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🔥 OUR SERVICES & PRICES",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Premium quality design and printing in Ethiopia",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }

                Text(
                    text = "See All →",
                    color = DiamondCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { onNavigate(AppScreen.SERVICES) }
                        .testTag("home_view_all_services")
                )
            }
        }

        // Horizontal Preview Row of Services
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(ServiceCatalog.services) { service ->
                    ServiceFeaturedCard(
                        service = service,
                        onSelect = { onSelectService(service) },
                        onOrder = { onStartOrder(service) }
                    )
                }
            }
        }

        // Why Bona Graphics Section
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "✨", fontSize = 20.sp)
                        Text(
                            text = "WHY BONA GRAPHICS?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    val benefits = listOf(
                        "📱 Easy ordering from your phone" to "Order in seconds via our intuitive app or direct Telegram.",
                        "🎨 Professional creative designs" to "Unique visual concepts made by skilled graphic specialists.",
                        "🖨️ Quality printing" to "High-density garment inks, UV-durable vinyls, and crisp finishes.",
                        "💰 Affordable prices in ETB" to "Transparent pricing starting from 350-500 ETB with no hidden costs.",
                        "💎 Custom designs made for you" to "Your Idea. Our Design. Your Style. Made exactly to your taste.",
                        "🏠 Convenient service from home" to "Stay comfortable; we handle the design, print, and delivery."
                    )

                    benefits.forEach { (title, subtitle) ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = DiamondCyan,
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(top = 2.dp)
                            )
                            Column {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Fast Order CTA Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(ElectricViolet.copy(alpha = 0.25f), DiamondCyan.copy(alpha = 0.25f))
                        )
                    )
                    .border(1.dp, DiamondCyan.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Need a custom design right now?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Calculate pricing, configure your T-Shirt, Banner, or Stickers, and send your request straight to our team in seconds.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Button(
                        onClick = { onNavigate(AppScreen.BUILDER) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DiamondCyan,
                            contentColor = DarkObsidian
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_bottom_cta_btn")
                    ) {
                        Text(
                            text = "Launch Order Builder 🚀",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceFeaturedCard(
    service: ServiceItem,
    onSelect: () -> Unit,
    onOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
        modifier = modifier
            .width(240.dp)
            .clickable { onSelect() }
            .testTag("featured_service_${service.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon + Price Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DiamondCyan.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = service.iconEmoji, fontSize = 24.sp)
                }

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = RadiantGold.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RadiantGold.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = if (service.isStartingPrice) "From ${service.priceEtb} ETB" else "${service.priceEtb} ETB",
                        color = RadiantGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = service.shortDescription,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            // Turnaround pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "⏱️", fontSize = 11.sp)
                Text(
                    text = service.estimatedTurnaround,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            // Quick Order Button
            Button(
                onClick = onOrder,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DiamondCyan.copy(alpha = 0.2f),
                    contentColor = DiamondCyan
                ),
                contentPadding = PaddingValues(vertical = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("featured_order_btn_${service.id}")
            ) {
                Text(
                    text = "Customize & Order",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
