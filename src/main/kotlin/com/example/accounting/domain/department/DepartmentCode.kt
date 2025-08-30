package com.example.accounting.domain.department

class DepartmentCode(
    val value: String,
) {
    companion object {
        private const val length = 4

        fun of(value: String): DepartmentCode {
            validate(value)
            return DepartmentCode(value)
        }

        private fun validate(value: String) {
            if (value.isBlank() || value.isEmpty()) {
                throw RuntimeException("部署コードは空にできません")
            }
            if (value.length != length) {
                throw RuntimeException("部署コードは${length}文字である必要があります")
            }
            if (!value.matches(Regex("^[0-9]{4}$"))) {
                throw RuntimeException("部署コードは4桁の数字で入力してください")
            }
        }
    }
}