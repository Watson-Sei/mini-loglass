package com.example.accounting.infrastructure

import com.example.accounting.domain.department.*
import org.springframework.stereotype.Repository

@Repository
class DepartmentJDBCRepository : DepartmentRepository {

    override fun list(): List<Department> {
        return listOf(
            Department.reconstruct(
                DepartmentCode.of("1000"),
                DepartmentName.of("東京営業部１課"),
                null,
            ),
            Department.reconstruct(
                DepartmentCode.of("1001"),
                DepartmentName.of("東京営業1課"),
                DepartmentCode.of("1000"),
            ),
            Department.reconstruct(
                DepartmentCode.of("1002"),
                DepartmentName.of("東京営業部２課"),
                DepartmentCode.of("1000"),
            ),
            Department.reconstruct(
                DepartmentCode.of("2000"),
                DepartmentName.of("大阪営業部"),
                null,
            ),
            Department.reconstruct(
                DepartmentCode.of("2001"),
                DepartmentName.of("大阪営業1課"),
                DepartmentCode.of("2000"),
            ),
            Department.reconstruct(
                DepartmentCode.of("2002"),
                DepartmentName.of("大阪営業2課"),
                DepartmentCode.of("2000"),
            ),
        )
    }

    override fun find(code: DepartmentCode): Department? {
        return null;
    }
    
    override fun insert(department: Department) {

    }
}