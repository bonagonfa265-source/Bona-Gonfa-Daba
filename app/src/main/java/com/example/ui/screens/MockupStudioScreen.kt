package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MockupStudioState
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MockupStudioScreen(
    state: MockupStudioState,
    onProductChange: (String) -> Unit,
    onColorChange: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onSubtextChange: (String) -> Unit,
    onBadgeChange: (String) -> Unit,
    onTextColorChange: (String) -> Unit,
    onApplyToOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val products = listOf("T-Shirt", "Roll-up Banner", "Die-cut Sticker", "Hoodie")
    val colors = listOf("Obsidian Black", "Pure White", "Royal Cyan", "Radiant Gold", "Navy Blue", "Crimson Red")
    val badges = listOf("💎", "👑", "🦁", "⚡", "🎨", "✝️", "⭐", "🔥", "🏆")
    val textColors = listOf("Diamond Cyan", "Radiant Gold", "Pure White", "Electric Violet")

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
                    text = "🎨 Live Mockup Studio",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Preview your T-Shirt, Banner, or Stickers in real-time before sending your custom order.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Live Simulated Canvas Preview
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Brush.linearGradient(listOf(DiamondCyan.copy(alpha = 0.5f), ElectricViolet.copy(alpha = 0.4f)))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mockup_preview_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Badge row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = DiamondCyan.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "LIVE SIMULATION: ${state.productType.uppercase()}",
                                color = DiamondCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = state.baseColor,
                            color = RadiantGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Interactive Custom Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(DarkSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        MockupVisualCanvas(state = state)
                    }

                    // Quick Note
                    Text(
                        text = "💡 Real output will be precision screen printed or high-res UV printed by Bona Graphics.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Product Selector
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Select Product Mockup:",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(products) { prod ->
                            val isSelected = prod == state.productType
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) DiamondCyan.copy(alpha = 0.25f) else DarkSurface,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) DiamondCyan else DarkCardBorder
                                ),
                                modifier = Modifier.clickable { onProductChange(prod) }
                            ) {
                                Text(
                                    text = prod,
                                    color = if (isSelected) DiamondCyan else TextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Color Palette Selector
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Product Base Color:",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        colors.forEach { col ->
                            val isSelected = col == state.baseColor
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) RadiantGold.copy(alpha = 0.2f) else DarkSurface,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) RadiantGold else DarkCardBorder
                                ),
                                modifier = Modifier.clickable { onColorChange(col) }
                            ) {
                                Text(
                                    text = col,
                                    color = if (isSelected) RadiantGold else TextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Badge & Icon Selector
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Print Graphic Badge / Symbol:",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(badges) { b ->
                            val isSelected = b == state.badgeSymbol
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) DiamondCyan.copy(alpha = 0.25f) else DarkSurface)
                                    .border(
                                        1.dp,
                                        if (isSelected) DiamondCyan else DarkCardBorder,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onBadgeChange(b) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = b, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }

        // Custom Text Inputs
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Custom Text & Typography:",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    OutlinedTextField(
                        value = state.artworkText,
                        onValueChange = onTextChange,
                        label = { Text("Primary Slogan / Brand Text") },
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
                            .testTag("mockup_text_input")
                    )

                    OutlinedTextField(
                        value = state.artworkSubtext,
                        onValueChange = onSubtextChange,
                        label = { Text("Sub-text / Event Year / Location") },
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
                            .testTag("mockup_subtext_input")
                    )

                    // Text Color
                    Text(
                        text = "Artwork Ink Color:",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        textColors.forEach { tc ->
                            val isSelected = tc == state.textColor
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) DiamondCyan.copy(alpha = 0.2f) else DarkSurface,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) DiamondCyan else DarkCardBorder
                                ),
                                modifier = Modifier.clickable { onTextColorChange(tc) }
                            ) {
                                Text(
                                    text = tc,
                                    color = if (isSelected) DiamondCyan else TextSecondary,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Transfer to Order Button
        item {
            Button(
                onClick = onApplyToOrder,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DiamondCyan,
                    contentColor = DarkObsidian
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("apply_mockup_order_btn")
            ) {
                Text(
                    text = "Transfer This Design to Order Builder 💎",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun MockupVisualCanvas(state: MockupStudioState) {
    val garmentColor = when (state.baseColor) {
        "Obsidian Black" -> Color(0xFF161920)
        "Pure White" -> Color(0xFFE2E8F0)
        "Royal Cyan" -> Color(0xFF007799)
        "Radiant Gold" -> Color(0xFFB48200)
        "Navy Blue" -> Color(0xFF102A43)
        "Crimson Red" -> Color(0xFF8B1E2F)
        else -> Color(0xFF1E232E)
    }

    val inkColorHex = when (state.textColor) {
        "Diamond Cyan" -> android.graphics.Color.parseColor("#00E5FF")
        "Radiant Gold" -> android.graphics.Color.parseColor("#FFB800")
        "Pure White" -> android.graphics.Color.parseColor("#FFFFFF")
        "Electric Violet" -> android.graphics.Color.parseColor("#C084FC")
        else -> android.graphics.Color.parseColor("#00E5FF")
    }

    val shadowColor = Color(0x33000000)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        when (state.productType) {
            "Roll-up Banner" -> {
                // Stand Base
                drawRoundRect(
                    color = Color(0xFF334155),
                    topLeft = Offset(w * 0.25f, h * 0.88f),
                    size = Size(w * 0.5f, h * 0.05f),
                    cornerRadius = CornerRadius(8f, 8f)
                )
                // Banner Canvas
                drawRoundRect(
                    color = garmentColor,
                    topLeft = Offset(w * 0.28f, h * 0.08f),
                    size = Size(w * 0.44f, h * 0.80f),
                    cornerRadius = CornerRadius(6f, 6f)
                )
                drawRoundRect(
                    color = Color(0x22FFFFFF),
                    topLeft = Offset(w * 0.28f, h * 0.08f),
                    size = Size(w * 0.44f, h * 0.80f),
                    cornerRadius = CornerRadius(6f, 6f),
                    style = Stroke(width = 3f)
                )
            }
            "Die-cut Sticker" -> {
                // Sticker circular/rounded contour
                drawCircle(
                    color = Color.White,
                    center = Offset(w * 0.5f, h * 0.5f),
                    radius = h * 0.38f
                )
                drawCircle(
                    color = garmentColor,
                    center = Offset(w * 0.5f, h * 0.5f),
                    radius = h * 0.35f
                )
            }
            else -> {
                // T-Shirt / Hoodie Silhouette
                val path = Path().apply {
                    val cx = w * 0.5f
                    // Collar neck opening
                    moveTo(cx - 45f, h * 0.15f)
                    quadraticTo(cx, h * 0.22f, cx + 45f, h * 0.15f)
                    // Right shoulder
                    lineTo(w * 0.76f, h * 0.22f)
                    // Right sleeve
                    lineTo(w * 0.86f, h * 0.38f)
                    lineTo(w * 0.74f, h * 0.44f)
                    // Right underarm
                    lineTo(w * 0.70f, h * 0.36f)
                    // Right torso
                    lineTo(w * 0.68f, h * 0.85f)
                    // Bottom hem
                    quadraticTo(cx, h * 0.88f, w * 0.32f, h * 0.85f)
                    // Left torso
                    lineTo(w * 0.30f, h * 0.36f)
                    // Left underarm
                    lineTo(w * 0.14f, h * 0.44f)
                    // Left sleeve
                    lineTo(w * 0.24f, h * 0.22f)
                    close()
                }

                drawPath(path = path, color = garmentColor)
                drawPath(path = path, color = Color(0x22FFFFFF), style = Stroke(width = 3f))

                // Inner collar crease
                drawArc(
                    color = shadowColor,
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(w * 0.5f - 40f, h * 0.12f),
                    size = Size(80f, 40f),
                    style = Stroke(width = 4f)
                )
            }
        }

        // Render Artwork & Text via Native Canvas Text Paint
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = inkColorHex
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
                isFakeBoldText = true
            }

            // Draw Badge Symbol
            val badgePaint = android.graphics.Paint().apply {
                textSize = 65f
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
            }
            drawText(state.badgeSymbol, w * 0.5f, h * 0.44f, badgePaint)

            // Draw Main Text
            paint.textSize = 34f
            val mainText = if (state.artworkText.length > 18) {
                state.artworkText.take(16) + ".."
            } else {
                state.artworkText
            }
            drawText(mainText.uppercase(), w * 0.5f, h * 0.53f, paint)

            // Draw Subtext
            paint.textSize = 20f
            paint.color = android.graphics.Color.argb(200, 240, 245, 255)
            val subText = if (state.artworkSubtext.length > 22) {
                state.artworkSubtext.take(20) + ".."
            } else {
                state.artworkSubtext
            }
            drawText(subText, w * 0.5f, h * 0.60f, paint)
        }
    }
}
