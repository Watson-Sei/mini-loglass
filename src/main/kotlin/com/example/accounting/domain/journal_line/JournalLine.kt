package com.example.accounting.domain.journal_line

import com.example.accounting.domain.account.Account
import com.example.accounting.domain.account.AccountCode
import com.example.accounting.domain.journal_line.JournalType
import com.example.accounting.domain.journal_line.JournalAmount

class JournalLine(
    val type: JournalType,
    val account : Account,
    val amount : JournalAmount,
) {
    companion object {
        fun create(
            type: JournalType,
            account: Account,
            amount : JournalAmount,
        ): JournalLine {
            validate(type, account, amount)
            return JournalLine(type, account, amount)
        }

        fun reconstruct(
            type: JournalType,
            account: Account,
            amount : JournalAmount,
        ): JournalLine {
            return JournalLine(type, account, amount)
        }

        private fun validate(type: JournalType, account: Account, amount: JournalAmount) {
            if(type != JournalType.DEBIT && type != JournalType.CREDIT) {
                throw RuntimeException("仕訳タイプは借方または貸方のみです")
            }

            if(account.parentCode != null) {
                throw RuntimeException("親科目が設定されている科目は使用できません")
            }

            if(amount.value <= 0) {
                throw RuntimeException("金額は1以上の数字を入力してください")
            }
        }
    }
}