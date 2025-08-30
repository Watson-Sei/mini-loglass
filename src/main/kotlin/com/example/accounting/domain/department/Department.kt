package com.example.accounting.domain.department

import com.example.accounting.domain.account.AccountType

class Department(
    val code: DepartmentCode,
    val name: DepartmentName,
    val parentCode: DepartmentCode?,
) {
    companion object {
        fun create(
            code: DepartmentCode,
            name: DepartmentName,
            parentDepartment: Department?,
        ): Department {
            validate(parentDepartment)
            return Department(code, name, parentDepartment?.code)
        }

        fun reconstruct(
            code: DepartmentCode,
            name: DepartmentName,
            parentCode: DepartmentCode?,
        ): Department {
            return Department(code, name, parentCode)
        }

        private fun validate(parentDepartment: Department?) {
            if (parentDepartment?.parentCode != null) {
                throw RuntimeException("部署は第二階層までしか設定できません")
            }
        }
    }
}