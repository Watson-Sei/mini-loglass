package com.example.accounting.apiController

import com.example.accounting.domain.team.Team
import com.example.accounting.usecase.GetTeamUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("apiTeamController")
@RequestMapping("/api")
class TeamController(
    private val getTeamUseCase: GetTeamUseCase,
) {
    
    @GetMapping("/team")
    fun get(): ResponseEntity<Team?> {
        val team = getTeamUseCase.execute()
        return ResponseEntity.ok(team)
    }
}