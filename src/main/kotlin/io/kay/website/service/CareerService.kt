package io.kay.website.service

import io.kay.website.api.model.CareerItem
import io.kay.website.api.model.Company
import io.kay.website.api.model.UpdateCareerItem
import io.kay.website.api.model.UpsertCompany
import io.kay.website.mapper.CareerMapper
import io.kay.website.repositories.CareerRepo
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
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

    fun updateCareerPath(personId: UUID, careerItems: List<UpdateCareerItem>): List<CareerItem> {
        if (!areCareerItemsValid(careerItems)) {
            throw BadRequestException("Career items end is before start")
        }

        LOGGER.info("Updating career path of person with id $personId")
        careerRepo.clearCareerOfPerson(personId)
        careerRepo.addNewCareerItemsOfPerson(personId, careerItems)
        return getCareerOfPerson(personId).toList()
    }

    private fun areCareerItemsValid(careerItems: List<UpdateCareerItem>): Boolean {
        return careerItems.filter { it.end != null }
            .map { it.start.until(it.end) }
            .all { !it.isNegative }
    }

    fun createCompany(company: UpsertCompany): Company {
        careerRepo.findCompanyByName(company.name)?.let {
            throw BadRequestException("Company ${company.name} already exists")
        }

        return transaction {
            val savedCompany = careerRepo.createCompany(company)
            careerMapper.toApiCompany(savedCompany)
        }
    }

    fun findCompanies(name: String?): List<Company> {
        LOGGER.info("Searching for companies with name '$name'")
        return transaction {
            val companies = careerRepo.searchCompanyByName(name)
            companies.map { careerMapper.toApiCompany(it) }
        }
    }

    fun findCompany(id: UUID): Company {
        return transaction {
            val company = careerRepo.findCompanyByUUID(id)
                ?: throw NotFoundException("Company with id $id not found")
            careerMapper.toApiCompany(company)
        }
    }

    fun updateCompany(id: UUID, upsertCompany: UpsertCompany): Company {
        return transaction {
            val company = careerRepo.updateCompany(id, upsertCompany)
            careerMapper.toApiCompany(company)
        }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(EducationService::class.java)
    }
}
