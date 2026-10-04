package io.kay.website.api

import io.kay.website.api.model.Company
import io.kay.website.api.model.UpsertCompany
import io.kay.website.service.CareerService
import jakarta.ws.rs.BadRequestException
import java.util.*

class CompanyResource(
    private val careerService: CareerService,
) : CompaniesApi {

    override fun createCompany(company: UpsertCompany?): Company {
        if (company == null) {
            throw BadRequestException("No request body was given")
        }
        return careerService.createCompany(company)
    }

    override fun getCompanies(name: String?): List<Company> {
        return careerService.findCompanies(name)
    }

    override fun getCompany(id: UUID?): Company? {
        if (id == null) {
            throw BadRequestException("Identifier is null")
        }
        return careerService.findCompany(id)
    }

    override fun updateCompany(id: UUID?, upsertCompany: UpsertCompany?): Company {
        if (id == null) {
            throw BadRequestException("Identifier is null")
        }
        if (upsertCompany == null) {
            throw BadRequestException("No request body was given")
        }
        return careerService.updateCompany(id, upsertCompany)
    }
}
