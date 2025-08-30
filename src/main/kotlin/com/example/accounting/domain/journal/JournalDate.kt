package com.example.accounting.domain.journal

import java.time.LocalDate

class JournalDate(
    val value: LocalDate
) {
    companion object {
        fun of(value: LocalDate): JournalDate {
            require(value != null) { "仕訳日付は必須です" }
            return JournalDate(value)
        }
    }
}