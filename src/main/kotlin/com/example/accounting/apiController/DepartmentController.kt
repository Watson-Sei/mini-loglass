package com.example.accounting.apiController

import com.example.accounting.domain.department.Department
import com.example.accounting.usecase.ListDepartmentUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("apiDepartmentController")
@RequestMapping("/api")
class DepartmentController(
    private val listUseCase: ListDepartmentUseCase,
) {
    @GetMapping("/departments")
    fun list(): ResponseEntity<List<Department>> {
        val departments = listUseCase.execute()
        return ResponseEntity.ok(departments)
    }
}