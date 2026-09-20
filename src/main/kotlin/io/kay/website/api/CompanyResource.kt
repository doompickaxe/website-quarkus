package io.kay.website.api

import io.kay.website.api.model.Company
import io.kay.website.service.CareerService
import jakarta.ws.rs.BadRequestException

class CompanyResource(
    private val careerService: CareerService,
) : CompaniesApi {

    override fun createCompany(company: Company?): Company {
        if (company == null) {
            throw BadRequestException("Company is null")
        }
        return careerService.createCompany(company)
    }
}
