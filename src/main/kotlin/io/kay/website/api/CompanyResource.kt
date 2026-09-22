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

    override fun getCompanies(name: String?): List<Company> {
        println(name?.length)
        return careerService.findCompanies(name)
    }
}
