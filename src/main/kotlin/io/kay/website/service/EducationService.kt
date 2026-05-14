package io.kay.website.service

import io.kay.website.api.model.EducationItem
import io.kay.website.mapper.EducationMapper
import io.kay.website.repositories.EducationRepo
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory
import java.util.*

@ApplicationScoped
class EducationService(
    private val educationRepo: EducationRepo,
    private val educationMapper: EducationMapper,
) {

    fun getEducationOfPerson(id: UUID): Iterable<EducationItem> {
        return transaction {
            val education = educationRepo.getEducationOfPerson(id)
            educationMapper.toApiEducations(education)
        }
    }

    fun updateEducationPath(personId: UUID, educationItems: List<EducationItem>): List<EducationItem> {
        if (!areEducationItemsValid(educationItems)) {
            throw BadRequestException("Education items end is before start")
        }

        LOGGER.info("Updating education path of person with id $personId")

        educationRepo.clearEducationOfPerson(personId)
        educationRepo.addNewEducationItemsOfPerson(personId, educationItems)
        return getEducationOfPerson(personId).toList()
    }

    private fun areEducationItemsValid(educationItems: List<EducationItem>): Boolean {
        return educationItems.filter { it.end != null }
            .map { it.start.until(it.end) }
            .all { !it.isNegative }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(EducationService::class.java)
    }
}
