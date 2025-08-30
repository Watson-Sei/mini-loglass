package com.example.accounting.apiController

import com.example.accounting.domain.journal.Journal
import com.example.accounting.usecase.ListJournalUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("apiJournalController")
@RequestMapping("/api")
class JournalController(
    private val listUseCase: ListJournalUseCase,
) {
    @GetMapping("/journals")
    fun list(): ResponseEntity<List<Journal>> {
        val journals = listUseCase.execute()
        return ResponseEntity.ok(journals)
    }
}