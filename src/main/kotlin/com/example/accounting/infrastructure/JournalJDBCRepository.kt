package com.example.accounting.infrastructure

import com.example.accounting.domain.department.DepartmentName
import com.example.accounting.domain.journal.Journal
import com.example.accounting.domain.journal.JournalDate
import com.example.accounting.domain.journal.JournalNumber
import com.example.accounting.domain.journal.JournalRepository
import com.example.accounting.domain.journal_line.JournalLine
import com.example.accounting.domain.journal_line.JournalType
import com.example.accounting.domain.journal_line.JournalAmount
import com.example.accounting.domain.account.Account
import com.example.accounting.domain.account.AccountCode
import com.example.accounting.domain.account.AccountName
import com.example.accounting.domain.account.AccountType
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class JournalJDBCRepository : JournalRepository {
    override fun list(): List<Journal> {
        return listOf(
            Journal.reconstruct(
                JournalDate.of(LocalDate.of(2024, 1, 1)),
                JournalNumber.of(1000000000),
                DepartmentName.of("東京営業1課"),
                listOf(
                    JournalLine.reconstruct(
                        JournalType.DEBIT,
                        Account.reconstruct(
                            AccountCode.of("3001"),
                            AccountName.of("現金"),
                            AccountType.ASSET,
                            AccountCode.of("3000")
                            ),
                        JournalAmount.of(1000),
                    ),
                    JournalLine.reconstruct(
                        JournalType.CREDIT,
                        Account.reconstruct(
                            AccountCode.of("1001"),
                            AccountName.of("売上高"),
                            AccountType.PROFIT,
                            AccountCode.of("1000")
                            ),
                        JournalAmount.of(1000),
                    ),
                )
            ),
            Journal.reconstruct(
                JournalDate.of(LocalDate.of(2024, 1, 3)),
                JournalNumber.of(1000000001),
                DepartmentName.of("東京営業2課"),
                listOf(
                    JournalLine.reconstruct(
                        JournalType.DEBIT,
                        Account.reconstruct(
                            AccountCode.of("3001"),
                            AccountName.of("現金"),
                            AccountType.ASSET,
                            AccountCode.of("3000")
                            ),
                        JournalAmount.of(2000),
                    ),
                    JournalLine.reconstruct(
                        JournalType.CREDIT,
                        Account.reconstruct(
                            AccountCode.of("1001"),
                            AccountName.of("売上高"),
                            AccountType.PROFIT,
                            AccountCode.of("1000")
                            ),
                        JournalAmount.of(2000),
                    ),
                )
            )
            // Journal.reconstruct(
            //     JournalData.of(2024, 1, 19),
            //     JournalNumber.of(1000000002),
            //     DepartmentName.of("東京営業2課"),
            //     listOf(
            //         JournalLine.reconstruct(
            //             Account.reconstruct("3002", "預金", "資産", "3000"),
            //             Amount.of(2000),
            //             LineType.DEBIT
            //         ),
            //         JournalLine.reconstruct(
            //             Account.reconstruct("1011", "預金利息", "収益", "1010"),
            //             Amount.of(2000),
            //             LineType.CREDIT
            //         ),
            //     )
            // ),
            // Journal.reconstruct(
            //     JournalDate.of(LocalDate.of(2024, 1, 20)),
            //     JournalNumber.of(1000000003),
            //     DepartmentName.of("東京営業1課"),
            //     listOf(
            //         JournalLine.reconstruct(
            //             Account.reconstruct(
            //                 JournalType.DEBIT,
            //                 AccountCode.of("2001"),
            //                 AccountName.of("給料"),
            //                 AccountType.LOSS,
            //                 AccountCode.of("2000")
            //                 ),
            //             JournalAmount.of(500),
            //         ),
            //         JournalLine.reconstruct(
            //             JournalType.DEBIT,
            //             Account.reconstruct(
            //                 AccountCode.of("2003"),
            //                 AccountName.of("支払手数料"),
            //                 AccountType.LOSS,
            //                 AccountCode.of("2000")
            //                 ),
            //             JournalAmount.of(100),
            //         ),
            //         JournalLine.reconstruct(
            //             JournalType.CREDIT,
            //             Account.reconstruct(
            //                 AccountCode.of("3001"),
            //                 AccountName.of("現金"),
            //                 AccountType.ASSET,
            //                 AccountCode.of("3000")
            //                 ),
            //             JournalAmount.of(600),
            //         ),
            //     )
            // ),
            // Journal.reconstruct(
            //     JournalData.of(2024, 1, 20),
            //     JournalNumber.of(1000000004),
            //     DepartmentName.of("東京営業1課"),
            //     listOf(
            //         JournalLine.reconstruct(
            //             Account.reconstruct("3001", "現金", "資産", "3000"),
            //             Amount.of(2000),
            //             LineType.DEBIT,
            //         ),
            //         JournalLine.reconstruct(
            //             Account.reconstruct("4001", "借入金", "負債", "4000"),
            //             Amount.of(2000),
            //             LineType.CREDIT,
            //         ),
            //     )
            // ),
        )
    }

}