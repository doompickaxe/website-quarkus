package io.kay.website.api

import io.kay.website.api.model.EducationItem
import io.kay.website.service.EducationService
import jakarta.ws.rs.NotFoundException
import java.util.*

class EducationResource(
    private val educationService: EducationService
) : EducationApi {

    override fun getEducationPath(id: UUID?): List<EducationItem> {
        if (id == null) {
            throw NotFoundException("Person not found")
        }

        return educationService.getEducationOfPerson(id).toList()
    }

    override fun updateEducationPath(id: UUID, educationItems: List<EducationItem>): List<EducationItem> {
        return educationService.updateEducationPath(id, educationItems)
    }
}
