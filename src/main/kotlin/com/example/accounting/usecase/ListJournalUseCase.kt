package com.example.accounting.usecase

import com.example.accounting.domain.journal.Journal
import com.example.accounting.domain.journal.JournalRepository
import org.springframework.stereotype.Service

@Service
class ListJournalUseCase(
    private val journalRepository: JournalRepository,
) {
    fun execute(): List<Journal> {
        return journalRepository.list()
    }
}