package com.example.accounting.domain.team

class TeamId(
    val value: String
) {
    companion object {
        fun of(value: String): TeamId {
            validate(value)
            return TeamId(value)
        }

        private fun validate(value: String) {
            if (value.isBlank() || value.isEmpty()) {
                throw RuntimeException("チームIDは空にできません")
            }
        }
    }
}
