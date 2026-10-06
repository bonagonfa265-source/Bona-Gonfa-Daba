package com.example.data.model

data class ServiceItem(
    val id: String,
    val title: String,
    val priceEtb: Int,
    val priceSuffix: String = "ETB",
    val isStartingPrice: Boolean = false,
    val iconEmoji: String,
    val category: ServiceCategory,
    val shortDescription: String,
    val fullDescription: String,
    val useCases: List<String>,
    val availableColors: List<String> = emptyList(),
    val availableSizes: List<String> = emptyList(),
    val availableOptions: List<String> = emptyList(),
    val estimatedTurnaround: String = "24 - 48 Hours"
)

enum class ServiceCategory(val displayName: String) {
    ALL("All Services"),
    APPAREL("Apparel"),
    PRINTING("Banners & Stickers"),
    BRANDING("Branding & Logo"),
    MARKETING("Flyers & Social Media"),
    STATIONERY("Certificates & Cards")
}

object ServiceCatalog {
    val services = listOf(
        ServiceItem(
            id = "tshirt",
            title = "Custom T-Shirt",
            priceEtb = 500,
            iconEmoji = "👕",
            category = ServiceCategory.APPAREL,
            shortDescription = "Personalized high-quality T-shirts for any special occasion.",
            fullDescription = "Get high-density durable custom printed T-shirts. Ideal for birthdays, corporate teams, church gatherings, graduation groups, couples, and brand merchandising. Premium breathable cotton fabric with vivid color retention.",
            useCases = listOf("Birthdays & Milestones", "Church & Youth Events", "Corporate & Team Uniforms", "Couple & Family Matching", "Brand Merchandise"),
            availableColors = listOf("Obsidian Black", "Pure White", "Royal Cyan", "Heather Grey", "Navy Blue", "Crimson Red"),
            availableSizes = listOf("S", "M", "L", "XL", "XXL", "3XL"),
            availableOptions = listOf("Front Chest Print", "Full Front Print", "Back Print", "Front + Back Dual Print (+150 ETB)", "Pocket Logo"),
            estimatedTurnaround = "24 - 48 Hours"
        ),
        ServiceItem(
            id = "banner",
            title = "Banner Design & Print",
            priceEtb = 600,
            iconEmoji = "🖼️",
            category = ServiceCategory.PRINTING,
            shortDescription = "Professional eye-catching banners for businesses and major events.",
            fullDescription = "Heavy-duty outdoor and indoor banners crafted with weather-resistant flex vinyl and high-resolution UV printing. Complete with aluminum eyelets or roll-up retractable stand options.",
            useCases = listOf("Business Signage & Grand Openings", "Conferences & Seminars", "Church & Ministry Events", "School & College Graduations", "Wedding & Birthday Backdrops"),
            availableColors = listOf("Full Color High-Res", "Glossy Vinyl", "Matte Anti-Glare"),
            availableSizes = listOf("1m x 1m", "2m x 1m", "3m x 1.5m", "Roll-Up Stand (85x200cm)"),
            availableOptions = listOf("Hemmed with Metal Eyelets", "Roll-Up Pull Stand Included (+800 ETB)", "Pole Pockets", "Outdoor Heavy-Flex Vinyl"),
            estimatedTurnaround = "24 - 48 Hours"
        ),
        ServiceItem(
            id = "sticker",
            title = "Custom Stickers",
            priceEtb = 650,
            iconEmoji = "🏷️",
            category = ServiceCategory.PRINTING,
            shortDescription = "Die-cut waterproof custom stickers for packaging and branding.",
            fullDescription = "Premium vinyl stickers with waterproof, scratch-resistant lamination. Perfect for product packaging, coffee cups, honey jars, laptop decals, car windows, and promotional giveaways.",
            useCases = listOf("Product Packaging & Jars", "Business Branding & Logos", "Takeout & Coffee Cups", "Laptop & Phone Decals", "Car & Window Stickers"),
            availableColors = listOf("Gloss Finish", "Matte Finish", "Transparent Clear", "Gold Foil Border"),
            availableSizes = listOf("Pack of 50 (5cm)", "Pack of 100 (5cm)", "Pack of 200 (Custom)"),
            availableOptions = listOf("Die-Cut (Custom Contour)", "Circle Cut", "Square / Rectangle", "Sheet Format", "Waterproof Laminated"),
            estimatedTurnaround = "24 - 48 Hours"
        ),
        ServiceItem(
            id = "logo",
            title = "Logo Design",
            priceEtb = 1200,
            isStartingPrice = true,
            iconEmoji = "🎨",
            category = ServiceCategory.BRANDING,
            shortDescription = "Distinctive modern brand logos with full source files & 3 revisions.",
            fullDescription = "Transform your company vision into an unforgettable brand identity. Includes 3 unique conceptual designs, color palette guides, high-resolution PNGs, vector SVG, and commercial usage rights.",
            useCases = listOf("New Startup Businesses", "Personal Brands & Influencers", "NGOs & Organizations", "Restaurants & Cafes", "Rebranding Existing Companies"),
            availableColors = listOf("Full Brand Color Guide", "Monochrome Variants Included", "Transparent PNG Included"),
            availableSizes = listOf("Starter Concept (1,200 ETB)", "Standard Brand Pack (2,000 ETB)", "Premium VIP Identity (3,500 ETB)"),
            availableOptions = listOf("Vector Source Files (AI/EPS)", "3 Revision Cycles", "Social Media Avatar Kit", "Color Guide & Typography Sheet"),
            estimatedTurnaround = "48 - 72 Hours"
        ),
        ServiceItem(
            id = "poster",
            title = "Poster & Flyer Design",
            priceEtb = 450,
            iconEmoji = "📢",
            category = ServiceCategory.MARKETING,
            shortDescription = "Striking promotional designs that grab attention and drive action.",
            fullDescription = "Visually magnetic posters and flyers designed to announce your events, business discounts, product launches, or parties. Delivered print-ready (PDF CMYK) and web-optimized.",
            useCases = listOf("Concerts, Parties & Club Nights", "Business Sales & Special Offers", "Church Conferences", "Educational Workshops", "Restaurant Menus"),
            availableColors = listOf("Vibrant High-Contrast", "Dark Mode Luxury", "Minimalist Clean"),
            availableSizes = listOf("A4 Standard", "A5 Handout", "A3 Display Poster", "Digital Only"),
            availableOptions = listOf("Single Sided", "Double Sided (+200 ETB)", "Print-Ready CMYK PDF", "Instagram/Telegram Format Ready"),
            estimatedTurnaround = "24 Hours"
        ),
        ServiceItem(
            id = "social",
            title = "Social Media Designs",
            priceEtb = 400,
            iconEmoji = "📱",
            category = ServiceCategory.MARKETING,
            shortDescription = "High-engagement graphics for TikTok, Telegram, Facebook & Instagram.",
            fullDescription = "Boost your digital engagement with trendy, pixel-perfect social media graphics. Tailored for TikTok profile banners, Telegram channel announcements, Instagram carousels, and Facebook promotional ads.",
            useCases = listOf("Telegram Channel Announcements", "TikTok Video Covers & Banners", "Instagram Feeds & Stories", "Facebook Business Ads", "YouTube Thumbnails"),
            availableColors = listOf("Trending Modern Aesthetic", "Brand Colors Matched"),
            availableSizes = listOf("Square (1080x1080)", "Story / TikTok (9:16)", "Telegram Banner (16:9)", "Bundle of 3 Graphics (1,000 ETB)"),
            availableOptions = listOf("Single Post Graphic", "3-Post Themed Pack (+600 ETB)", "Animated Story Graphic (+300 ETB)", "Editable Canva/Figma Link"),
            estimatedTurnaround = "24 Hours"
        ),
        ServiceItem(
            id = "certificate",
            title = "Certificates & Invitations",
            priceEtb = 350,
            iconEmoji = "📜",
            category = ServiceCategory.STATIONERY,
            shortDescription = "Elegant certificates, wedding invitations & milestone stationery.",
            fullDescription = "Prestigious certificate templates with ornate borders and sophisticated typography, as well as heartfelt wedding and VIP event invitations designed with love and elegance.",
            useCases = listOf("Wedding & Engagement Invitations", "Training & Workshop Certificates", "Honorary & Appreciation Awards", "Graduation Celebration Cards", "VIP Gala Access Badges"),
            availableColors = listOf("Gold Luxury Accents", "Royal Blue & Cream", "Emerald Elegance", "Classic Monochrome"),
            availableSizes = listOf("A4 Certificate", "5x7 inch Invitation", "Custom Card Size"),
            availableOptions = listOf("Custom Names Merging", "Digital Print-Ready File", "Linen Textured Cardstock Printing", "Envelope Design Included"),
            estimatedTurnaround = "24 - 48 Hours"
        )
    )

    fun getServiceById(id: String): ServiceItem? = services.find { it.id == id }
}
