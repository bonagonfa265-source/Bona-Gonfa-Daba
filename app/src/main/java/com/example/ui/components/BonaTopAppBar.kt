package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.IntentHelper

@Composable
fun BonaTopAppBar(
    title: String,
    subtitle: String = "Design & Printing 💎🖤",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface(
        color = DarkSurface,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Diamond Emblem Badge
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(DiamondCyan, ElectricViolet)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💎",
                        fontSize = 20.sp
                    )
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = DiamondCyan,
                        fontSize = 11.sp
                    )
                }
            }

            // Quick Action Buttons (Telegram & Call)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Telegram Quick Action
                IconButton(
                    onClick = { IntentHelper.openTelegram(context) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(TelegramBlue.copy(alpha = 0.15f))
                        .testTag("topbar_telegram_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Telegram @Bonagraphics",
                        tint = TelegramBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Call Quick Action
                IconButton(
                    onClick = { IntentHelper.dialPhoneNumber(context, IntentHelper.PHONE_PRIMARY) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(DiamondCyan.copy(alpha = 0.15f))
                        .testTag("topbar_call_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Bona Graphics",
                        tint = DiamondCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
