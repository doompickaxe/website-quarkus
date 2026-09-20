package io.kay.website.api

import io.kay.website.api.model.Company
import io.kay.website.service.CareerService

class CompanyResource(
    private val careerService: CareerService,
) : CompaniesApi {

    override fun createCompany(company: Company): Company {
        TODO("Not yet implemented")
    }
}
