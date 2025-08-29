package com.example.accounting.domain.team

class TeamName(
    val value: String
) {
    companion object {
        private const val maxLength = 100

        fun of(value: String): TeamName {
            validate(value)
            return TeamName(value)
        }

        private fun validate(value: String) {
            if (value.isBlank() || value.isEmpty()) {
                throw RuntimeException("チーム名は空にできません")
            }
            if (value.length > maxLength) {
                throw RuntimeException("チーム名は${maxLength}文字以下である必要があります")
            }
        }
    }
}
