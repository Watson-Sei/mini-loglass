package com.example.accounting.infrastructure

import com.example.accounting.domain.team.Team
import com.example.accounting.domain.team.TeamId
import com.example.accounting.domain.team.TeamName
import com.example.accounting.domain.team.TeamRepository
import jooq.tables.Team.TEAM
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class TeamJDBCRepository(
    private val jooq: DSLContext,
) : TeamRepository {

    override fun findFirst(): Team? {
        return jooq.selectFrom(TEAM)
            .limit(1)
            .fetchOne()
            ?.let { teamRecord ->
                Team.reconstruct(
                    TeamId.of(teamRecord[TEAM.ID].toString()),
                    TeamName.of(teamRecord[TEAM.NAME])
                )
            }
    }
}
