package com.example.accounting.domain.journal

import com.example.accounting.domain.department.DepartmentName
import com.example.accounting.domain.journal.JournalNumber
import com.example.accounting.domain.journal.JournalDate
import com.example.accounting.domain.journal_line.JournalLine
import java.time.LocalDate

class Journal(
    val date: JournalDate,
    val journalNumber: JournalNumber,
    val DepartmentName: DepartmentName,
    val lines: List<JournalLine>
) {
    companion object {
        fun create(
            date: JournalDate,
            journalNumber: JournalNumber,
            departmentName: DepartmentName,
            lines: List<JournalLine>
        ): Journal {
            return Journal(date, journalNumber, departmentName, lines)
        }

        fun reconstruct(
            date: JournalDate,
            journalNumber: JournalNumber,
            departmentName: DepartmentName,
            lines: List<JournalLine>
        ): Journal {
            require(lines.isNotEmpty()) { "仕訳行は1行以上必要です" }
            return Journal(date, journalNumber, departmentName, lines)
        }

        private fun validate(lines: List<JournalLine>) {
            if (lines.isEmpty()) {
                throw RuntimeException("仕訳行は1行以上必要です")
            }
        }
    }
}