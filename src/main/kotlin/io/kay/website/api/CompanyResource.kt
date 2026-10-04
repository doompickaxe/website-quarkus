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
            throw BadRequestException("Company is null")
        }
        return careerService.createCompany(company)
    }

    override fun getCompanies(name: String?): List<Company> {
        return careerService.findCompanies(name)
    }

    override fun getCompany(id: UUID?): Company? {
        TODO("Not yet implemented")
    }

    override fun updateCompany(id: UUID?, upsertCompany: UpsertCompany?): Company {
        TODO("Not yet implemented")
    }
}
