package com.example.accounting.domain.journal

class JournalNumber(
    val value: Long
) {
    companion object {
        private const val length = 10

        fun of(value: Long): JournalNumber {
            validate(value)
            return JournalNumber(value)
        }

        private fun validate(value: Long) {
            if (value.toString().length > length) {
                throw RuntimeException("仕訳Noは${length}桁以内で入力してください")
            }
        }
    }
}