package com.simpletech.domain.models

enum class SportCategory(val code: String) {
    FOOTBALL("Foot"),
    BASKETBALL("Bask"),
    TENNIS("Tenn"),
    TABLE_TENNIS("Tabl"),
    VOLLEYBALL("Voll"),
    ESPORTS("Esps"),
    ICE_HOCKEY("IceH"),
    HANDBALL("Hand"),
    SNOOKER("Snoo"),
    FUTSAL("Futs"),
    NOTREGISTERED("");

    companion object {
        fun fromCode(code: String): SportCategory =
            entries.find { it.code.equals(code, ignoreCase = true) } ?: NOTREGISTERED
    }
}