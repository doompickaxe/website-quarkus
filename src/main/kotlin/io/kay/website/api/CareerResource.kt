package io.kay.website.api

import io.kay.website.api.model.CareerItem
import io.kay.website.service.CareerService
import jakarta.ws.rs.NotFoundException
import java.util.*

class CareerResource(
    private val careerService: CareerService,
) : CareerApi {

    override fun getCareerPath(id: UUID?): List<CareerItem> {
        if (id == null) {
            throw NotFoundException("Person not found")
        }

        return careerService.getCareerOfPerson(id).toList()
    }

    override fun updateCareerPath(id: UUID, careerItems: List<CareerItem>): List<CareerItem> {
        return careerService.updateCareerPath(id, careerItems)
    }
}
