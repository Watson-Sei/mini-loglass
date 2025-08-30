package com.example.accounting.usecase

import com.example.accounting.domain.department.Department
import com.example.accounting.domain.department.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class ListDepartmentUseCase(
    private val departmentRepository: DepartmentRepository,
) {
    fun execute(): List<Department> {
        return departmentRepository.list()
    }
}