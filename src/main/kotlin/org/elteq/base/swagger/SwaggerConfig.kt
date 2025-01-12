//package com.base.swagger
//
//import jakarta.ws.rs.core.Application
//import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
//import org.eclipse.microprofile.openapi.annotations.info.Contact
//import org.eclipse.microprofile.openapi.annotations.info.Info
//import org.eclipse.microprofile.openapi.annotations.info.License
//import org.eclipse.microprofile.openapi.annotations.tags.Tag
//
//@OpenAPIDefinition(
//    tags = [
//        Tag(name = "Languages", description = "Supported countries languages"),
//        Tag(name = "Currencies", description = "Supported countries currencies"),
//        Tag(name = "Country", description = "Supported countries"),
//        Tag(name = "Payment provider", description = "Payment providers"),
//        Tag(name = "Subscribers payment method", description = "Subscribers payment method"),
//        Tag(name = "Institution", description = "Institutions"),
//        Tag(name = "Institution configuration details", description = "Institution configuration details"),
//        Tag(name = "Institution product", description = "Institution product"),
//        Tag(name = "Institution product setting", description = "Institution product setting"),
//        Tag(name = "Institution product setting price", description = "Institution product setting price"),
//        Tag(name = "Subscription", description = "Subscriptions for each product of institutions"),
//        Tag(name = "Subscription price", description = "Institution subscription price"),
//        Tag(name = "Billing cycle unit", description = "Institution subscription billing cycle unit"),
//        Tag(name = "Billing cycle", description = "Institution subscription billing cycle"),
//        Tag(name = "Validity period", description = "Institution subscription validity period"),
//        Tag(name = "Customers", description = "Users"),
//        Tag(name = "Subscribers", description = "Users who have subscribed to any subscription"),
//        Tag(name = "User subscription", description = "Users linked to their subscriptions"),
//        Tag(name = "Transactions", description = "Auto debit transactions"),
//        Tag(name = "Transactions prices", description = "Auto debit transactions prices"),
//        Tag(name = "Auto debit", description = "Auto debit"),
//    ],
//    info = Info(
//        title = "Subscription service with auto-debit",
//        version = "0.0.1",
//        contact = Contact(
//            name = "Nsano Ltd",
//            url = "https://nsano.com/contact",
//            email = "contact@nsano.com"
//        ),
//        license = License(name = "Nsano", url = "https://nsano.com")
//    )
//)
//class SwaggerConfig : Application()