package io.kay.website.service

import io.kay.website.api.model.CareerItem
import io.kay.website.api.model.Company
import io.kay.website.mapper.CareerMapper
import io.kay.website.repositories.CareerRepo
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory
import java.util.*

@ApplicationScoped
class CareerService(private val careerRepo: CareerRepo, private val careerMapper: CareerMapper) {

    fun getCareerOfPerson(id: UUID): Iterable<CareerItem> {
        return transaction {
            val career = careerRepo.getCareerOfPerson(id)
            careerMapper.toApiCareers(career)
        }
    }

    fun updateCareerPath(personId: UUID, careerItems: List<CareerItem>): List<CareerItem> {
        if (!areCareerItemsValid(careerItems)) {
            throw BadRequestException("Career items end is before start")
        }

        LOGGER.info("Updating career path of person with id $personId")
        careerRepo.clearCareerOfPerson(personId)
        careerRepo.addNewCareerItemsOfPerson(personId, careerItems)
        return getCareerOfPerson(personId).toList()
    }

    private fun areCareerItemsValid(careerItems: List<CareerItem>): Boolean {
        return careerItems.filter { it.end != null }
            .map { it.start.until(it.end) }
            .all { !it.isNegative }
    }

    fun createCompany(company: Company): Company {
        careerRepo.findCompanyByName(company.name)?.let {
            throw BadRequestException("Company ${company.name} already exists")
        }

        return transaction {
            val savedCompany = careerRepo.createCompany(company)
            careerMapper.toApiCompany(savedCompany)
        }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(EducationService::class.java)
    }
}
