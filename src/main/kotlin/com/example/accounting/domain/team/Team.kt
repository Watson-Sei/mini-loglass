package com.example.accounting.domain.team

class Team(
    val id: TeamId,
    val name: TeamName,
) {
    companion object {
        fun create(
            id: TeamId,
            name: TeamName,
        ): Team = Team(id, name)

        fun reconstruct(
            id: TeamId,
            name: TeamName,
        ): Team = Team(id, name)
    }
}
