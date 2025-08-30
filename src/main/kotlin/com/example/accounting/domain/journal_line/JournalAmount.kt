package com.example.accounting.domain.journal_line

class JournalAmount(val value: Int) {
    companion object {
        fun of(value: Int): JournalAmount {
            if(value <= 0) throw RuntimeException("金額は1以上の数字を入力してください")
            return JournalAmount(value)
        }
    }
}