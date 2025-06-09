package io.kay.website.service

import io.kay.website.api.model.EducationItem
import io.kay.website.mapper.EducationMapper
import io.kay.website.repositories.EducationRepo
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@ApplicationScoped
class EducationService(private val educationRepo: EducationRepo, private val educationMapper: EducationMapper) {

    fun getEducationOfPerson(id: UUID): Iterable<EducationItem> {
        return transaction {
            val education = educationRepo.getEducationOfPerson(id)
            educationMapper.toApiEducations(education)
        }
    }
}
