package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.OrderRepository
import com.example.data.model.OrderEntity
import com.example.data.model.ServiceCatalog
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen(val title: String, val iconEmoji: String) {
    HOME("Home", "🏠"),
    SERVICES("Services", "🎨"),
    MOCKUP("Studio", "👕"),
    BUILDER("Order Now", "✨"),
    ORDERS("My Orders", "📦"),
    CONTACT("Contact", "📲")
}

data class OrderFormState(
    val selectedServiceId: String = "tshirt",
    val customerName: String = "",
    val customerPhone: String = "",
    val customerAddress: String = "",
    val telegramUsername: String = "",
    val selectedColor: String = "Obsidian Black",
    val selectedSize: String = "L",
    val selectedOption: String = "Front Chest Print",
    val customText: String = "",
    val quantity: Int = 1,
    val notes: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
)

data class MockupStudioState(
    val productType: String = "T-Shirt",
    val baseColor: String = "Obsidian Black",
    val artworkText: String = "BONA GRAPHICS",
    val artworkSubtext: String = "Creative Printing Studio",
    val badgeSymbol: String = "💎",
    val printPosition: String = "Center Chest",
    val textColor: String = "Diamond Cyan"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderRepository

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
    val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedServiceDetail = MutableStateFlow<ServiceItem?>(null)
    val selectedServiceDetail: StateFlow<ServiceItem?> = _selectedServiceDetail.asStateFlow()

    private val _orderFormState = MutableStateFlow(OrderFormState())
    val orderFormState: StateFlow<OrderFormState> = _orderFormState.asStateFlow()

    private val _mockupState = MutableStateFlow(MockupStudioState())
    val mockupState: StateFlow<MockupStudioState> = _mockupState.asStateFlow()

    private val _activeOrderModal = MutableStateFlow<OrderEntity?>(null)
    val activeOrderModal: StateFlow<OrderEntity?> = _activeOrderModal.asStateFlow()

    private val _selectedOrderHistoryItem = MutableStateFlow<OrderEntity?>(null)
    val selectedOrderHistoryItem: StateFlow<OrderEntity?> = _selectedOrderHistoryItem.asStateFlow()

    private val _orderFilter = MutableStateFlow("ALL") // ALL, ACTIVE, COMPLETED
    val orderFilter: StateFlow<String> = _orderFilter.asStateFlow()

    val allOrders: StateFlow<List<OrderEntity>>

    init {
        val database = AppDatabase.getInstance(application)
        repository = OrderRepository(database.orderDao())
        allOrders = repository.allOrders.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        viewModelScope.launch {
            repository.ensureSampleOrderIfEmpty()
        }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setCategory(category: ServiceCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openServiceDetail(service: ServiceItem) {
        _selectedServiceDetail.value = service
    }

    fun closeServiceDetail() {
        _selectedServiceDetail.value = null
    }

    fun startOrderForService(service: ServiceItem) {
        _orderFormState.value = _orderFormState.value.copy(
            selectedServiceId = service.id,
            selectedColor = service.availableColors.firstOrNull() ?: "",
            selectedSize = service.availableSizes.firstOrNull() ?: "",
            selectedOption = service.availableOptions.firstOrNull() ?: "",
            errorMessage = null
        )
        _selectedServiceDetail.value = null
        _currentScreen.value = AppScreen.BUILDER
    }

    fun applyMockupToOrder() {
        val mockup = _mockupState.value
        val serviceId = when (mockup.productType) {
            "T-Shirt" -> "tshirt"
            "Roll-up Banner" -> "banner"
            "Die-cut Sticker" -> "sticker"
            else -> "tshirt"
        }
        val targetService = ServiceCatalog.getServiceById(serviceId)

        _orderFormState.value = _orderFormState.value.copy(
            selectedServiceId = serviceId,
            selectedColor = mockup.baseColor,
            selectedOption = mockup.printPosition,
            customText = "${mockup.artworkText} (${mockup.artworkSubtext}) [${mockup.badgeSymbol}]",
            notes = "Configured in Studio Mockup: ${mockup.productType} in ${mockup.baseColor} with ${mockup.badgeSymbol} badge and text color ${mockup.textColor}."
        )
        _currentScreen.value = AppScreen.BUILDER
    }

    // Order Form Mutators
    fun updateSelectedServiceId(serviceId: String) {
        val service = ServiceCatalog.getServiceById(serviceId)
        _orderFormState.value = _orderFormState.value.copy(
            selectedServiceId = serviceId,
            selectedColor = service?.availableColors?.firstOrNull() ?: "",
            selectedSize = service?.availableSizes?.firstOrNull() ?: "",
            selectedOption = service?.availableOptions?.firstOrNull() ?: ""
        )
    }

    fun updateCustomerName(name: String) {
        _orderFormState.value = _orderFormState.value.copy(customerName = name)
    }

    fun updateCustomerPhone(phone: String) {
        _orderFormState.value = _orderFormState.value.copy(customerPhone = phone)
    }

    fun updateCustomerAddress(address: String) {
        _orderFormState.value = _orderFormState.value.copy(customerAddress = address)
    }

    fun updateTelegramUsername(username: String) {
        _orderFormState.value = _orderFormState.value.copy(telegramUsername = username)
    }

    fun updateSelectedColor(color: String) {
        _orderFormState.value = _orderFormState.value.copy(selectedColor = color)
    }

    fun updateSelectedSize(size: String) {
        _orderFormState.value = _orderFormState.value.copy(selectedSize = size)
    }

    fun updateSelectedOption(option: String) {
        _orderFormState.value = _orderFormState.value.copy(selectedOption = option)
    }

    fun updateCustomText(text: String) {
        _orderFormState.value = _orderFormState.value.copy(customText = text)
    }

    fun updateQuantity(qty: Int) {
        if (qty in 1..500) {
            _orderFormState.value = _orderFormState.value.copy(quantity = qty)
        }
    }

    fun updateNotes(notes: String) {
        _orderFormState.value = _orderFormState.value.copy(notes = notes)
    }

    fun calculateCurrentTotalEtb(): Int {
        val state = _orderFormState.value
        val service = ServiceCatalog.getServiceById(state.selectedServiceId) ?: return 0
        var unitPrice = service.priceEtb

        // Specific Addons based on selected option
        if (state.selectedOption.contains("Dual Print") || state.selectedOption.contains("+150")) {
            unitPrice += 150
        } else if (state.selectedOption.contains("Roll-Up Pull Stand") || state.selectedOption.contains("+800")) {
            unitPrice += 800
        } else if (state.selectedOption.contains("Double Sided") || state.selectedOption.contains("+200")) {
            unitPrice += 200
        } else if (state.selectedOption.contains("Pack of 100")) {
            unitPrice += 400
        }

        return unitPrice * state.quantity
    }

    fun submitOrder() {
        val form = _orderFormState.value
        if (form.customerName.isBlank()) {
            _orderFormState.value = form.copy(errorMessage = "Please enter your name")
            return
        }
        if (form.customerPhone.isBlank() || form.customerPhone.length < 9) {
            _orderFormState.value = form.copy(errorMessage = "Please enter a valid phone number (e.g. 0919199459)")
            return
        }

        val service = ServiceCatalog.getServiceById(form.selectedServiceId)
        val serviceTitle = service?.title ?: "Custom Design Order"
        val total = calculateCurrentTotalEtb()
        val orderNum = "BG-${Random.nextInt(1000, 9999)}"

        val newOrder = OrderEntity(
            orderNumber = orderNum,
            serviceId = form.selectedServiceId,
            serviceTitle = serviceTitle,
            customerName = form.customerName.trim(),
            customerPhone = form.customerPhone.trim(),
            customerAddress = form.customerAddress.trim(),
            telegramUsername = form.telegramUsername.trim(),
            selectedColor = form.selectedColor,
            selectedSize = form.selectedSize,
            selectedOption = form.selectedOption,
            customText = form.customText.trim(),
            quantity = form.quantity,
            notes = form.notes.trim(),
            totalPriceEtb = total,
            status = "Order Placed",
            createdAtMillis = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.insertOrder(newOrder)
            _activeOrderModal.value = newOrder
            // Reset error
            _orderFormState.value = form.copy(errorMessage = null)
        }
    }

    fun dismissActiveOrderModal() {
        _activeOrderModal.value = null
    }

    // Mockup Studio Mutators
    fun updateMockupProduct(product: String) {
        _mockupState.value = _mockupState.value.copy(productType = product)
    }

    fun updateMockupColor(color: String) {
        _mockupState.value = _mockupState.value.copy(baseColor = color)
    }

    fun updateMockupText(text: String) {
        _mockupState.value = _mockupState.value.copy(artworkText = text)
    }

    fun updateMockupSubtext(subtext: String) {
        _mockupState.value = _mockupState.value.copy(artworkSubtext = subtext)
    }

    fun updateMockupBadge(badge: String) {
        _mockupState.value = _mockupState.value.copy(badgeSymbol = badge)
    }

    fun updateMockupPosition(pos: String) {
        _mockupState.value = _mockupState.value.copy(printPosition = pos)
    }

    fun updateMockupTextColor(color: String) {
        _mockupState.value = _mockupState.value.copy(textColor = color)
    }

    // Order History Actions
    fun setOrderFilter(filter: String) {
        _orderFilter.value = filter
    }

    fun selectHistoryOrder(order: OrderEntity?) {
        _selectedOrderHistoryItem.value = order
    }

    fun deleteOrder(orderId: Long) {
        viewModelScope.launch {
            repository.deleteOrderById(orderId)
            if (_selectedOrderHistoryItem.value?.id == orderId) {
                _selectedOrderHistoryItem.value = null
            }
        }
    }

    fun updateOrderStatus(order: OrderEntity, newStatus: String) {
        viewModelScope.launch {
            val updated = order.copy(status = newStatus)
            repository.updateOrder(updated)
            if (_selectedOrderHistoryItem.value?.id == order.id) {
                _selectedOrderHistoryItem.value = updated
            }
        }
    }
}
