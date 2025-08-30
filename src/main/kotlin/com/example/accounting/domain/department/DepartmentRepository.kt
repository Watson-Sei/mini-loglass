package com.example.accounting.domain.department

interface DepartmentRepository {
    fun find(code: DepartmentCode): Department?
    fun list(): List<Department>
    fun insert(department: Department)
}