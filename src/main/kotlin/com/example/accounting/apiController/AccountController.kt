package com.example.accounting.apiController

import com.example.accounting.domain.account.Account
import com.example.accounting.usecase.CreateAccountRequest
import com.example.accounting.usecase.CreateAccountUseCase
import com.example.accounting.usecase.ListAccountUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("apiAccountController")
@RequestMapping("/api")
class AccountController(
    private val listUseCase: ListAccountUseCase,
    private val createUseCase: CreateAccountUseCase,
) {
    
    @GetMapping("/accounts")
    fun list(): ResponseEntity<List<Account>> {
        val accounts = listUseCase.execute()
        return ResponseEntity.ok(accounts)
    }

    @PostMapping("/accounts")
    fun create(@RequestBody params: CreateAccountRequest): ResponseEntity<Void> {
        createUseCase.execute(params.code, params.name, params.accountType, params.parentCode)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
} 
