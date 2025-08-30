package com.example.accounting.domain.department

class DepartmentName(
    val value: String
) {
    companion object {
        private const val maxLength = 50

        fun of(value: String): DepartmentName {
            validate(value)
            return DepartmentName(value)
        }

        private fun validate(value: String) {
            if (value.isBlank() || value.isEmpty()) {
                throw RuntimeException("部署名は空欄にできません")
            }
            if (value.length > maxLength) {
                throw RuntimeException("部署名は${maxLength}文字以内で入力してください")
            }
        }
    }
}