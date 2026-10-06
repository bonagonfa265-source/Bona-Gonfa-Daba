package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.components.BonaBottomBar
import com.example.ui.components.BonaTopAppBar
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MockupStudioScreen
import com.example.ui.screens.OrderBuilderScreen
import com.example.ui.screens.OrdersHistoryScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BonaGraphicsApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BonaGraphicsApp(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedServiceDetail by viewModel.selectedServiceDetail.collectAsStateWithLifecycle()
    val orderFormState by viewModel.orderFormState.collectAsStateWithLifecycle()
    val mockupState by viewModel.mockupState.collectAsStateWithLifecycle()
    val activeOrderModal by viewModel.activeOrderModal.collectAsStateWithLifecycle()
    val orders by viewModel.allOrders.collectAsStateWithLifecycle()
    val orderFilter by viewModel.orderFilter.collectAsStateWithLifecycle()
    val selectedOrderHistoryItem by viewModel.selectedOrderHistoryItem.collectAsStateWithLifecycle()

    // BackHandler: Return to Home if on another screen
    if (currentScreen != AppScreen.HOME) {
        BackHandler {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    Scaffold(
        topBar = {
            BonaTopAppBar(
                title = "BONA G 💎✨",
                subtitle = when (currentScreen) {
                    AppScreen.HOME -> "Design • Print • Order from Home"
                    AppScreen.SERVICES -> "Services & Prices • Order from Home"
                    AppScreen.MOCKUP -> "Live Mockup Studio 👕"
                    AppScreen.BUILDER -> "Custom Order Builder ✨"
                    AppScreen.ORDERS -> "Order Tracking & History 📦"
                    AppScreen.CONTACT -> "Direct Connect & Support 📲"
                }
            )
        },
        bottomBar = {
            BonaBottomBar(
                currentScreen = currentScreen,
                onScreenSelected = { screen ->
                    viewModel.navigateTo(screen)
                }
            )
        },
        containerColor = DarkObsidian
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkObsidian)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    AppScreen.HOME -> HomeScreen(
                        onNavigate = { viewModel.navigateTo(it) },
                        onSelectService = { service ->
                            viewModel.openServiceDetail(service)
                            viewModel.navigateTo(AppScreen.SERVICES)
                        },
                        onStartOrder = { service ->
                            viewModel.startOrderForService(service)
                        }
                    )

                    AppScreen.SERVICES -> ServicesScreen(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.setCategory(it) },
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        onStartOrder = { service ->
                            viewModel.startOrderForService(service)
                        },
                        activeDetailService = selectedServiceDetail,
                        onOpenDetail = { viewModel.openServiceDetail(it) },
                        onCloseDetail = { viewModel.closeServiceDetail() }
                    )

                    AppScreen.MOCKUP -> MockupStudioScreen(
                        state = mockupState,
                        onProductChange = { viewModel.updateMockupProduct(it) },
                        onColorChange = { viewModel.updateMockupColor(it) },
                        onTextChange = { viewModel.updateMockupText(it) },
                        onSubtextChange = { viewModel.updateMockupSubtext(it) },
                        onBadgeChange = { viewModel.updateMockupBadge(it) },
                        onTextColorChange = { viewModel.updateMockupTextColor(it) },
                        onApplyToOrder = { viewModel.applyMockupToOrder() }
                    )

                    AppScreen.BUILDER -> OrderBuilderScreen(
                        formState = orderFormState,
                        totalEtb = viewModel.calculateCurrentTotalEtb(),
                        onServiceSelected = { viewModel.updateSelectedServiceId(it) },
                        onCustomerNameChange = { viewModel.updateCustomerName(it) },
                        onCustomerPhoneChange = { viewModel.updateCustomerPhone(it) },
                        onCustomerAddressChange = { viewModel.updateCustomerAddress(it) },
                        onTelegramUsernameChange = { viewModel.updateTelegramUsername(it) },
                        onColorSelected = { viewModel.updateSelectedColor(it) },
                        onSizeSelected = { viewModel.updateSelectedSize(it) },
                        onOptionSelected = { viewModel.updateSelectedOption(it) },
                        onCustomTextChange = { viewModel.updateCustomText(it) },
                        onQuantityChange = { viewModel.updateQuantity(it) },
                        onNotesChange = { viewModel.updateNotes(it) },
                        onSubmitOrder = { viewModel.submitOrder() },
                        activeOrderModal = activeOrderModal,
                        onDismissModal = { viewModel.dismissActiveOrderModal() }
                    )

                    AppScreen.ORDERS -> OrdersHistoryScreen(
                        orders = orders,
                        filter = orderFilter,
                        onFilterChange = { viewModel.setOrderFilter(it) },
                        selectedOrder = selectedOrderHistoryItem,
                        onSelectOrder = { viewModel.selectHistoryOrder(it) },
                        onDeleteOrder = { viewModel.deleteOrder(it) },
                        onUpdateStatus = { order, status -> viewModel.updateOrderStatus(order, status) },
                        onNavigateToBuilder = { viewModel.navigateTo(AppScreen.BUILDER) }
                    )

                    AppScreen.CONTACT -> ContactScreen()
                }
            }
        }
    }
}
