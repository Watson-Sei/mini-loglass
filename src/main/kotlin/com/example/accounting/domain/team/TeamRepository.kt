package com.example.accounting.domain.team

interface TeamRepository {
    fun findFirst(): Team?
}
