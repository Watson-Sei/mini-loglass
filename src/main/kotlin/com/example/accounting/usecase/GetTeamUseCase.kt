package com.example.accounting.usecase

import com.example.accounting.domain.team.Team
import com.example.accounting.domain.team.TeamRepository
import org.springframework.stereotype.Service

@Service
class GetTeamUseCase(
    private val teamRepository: TeamRepository,
) {
    fun execute(): Team? = teamRepository.findFirst()
}
