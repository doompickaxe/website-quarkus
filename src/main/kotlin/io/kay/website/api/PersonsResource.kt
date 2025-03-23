package io.kay.website.api

import io.kay.website.api.model.CareerItem
import io.kay.website.api.model.EducationItem
import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.service.PersonService
import jakarta.ws.rs.NotFoundException
import java.util.*

class PersonsResource(private val personService: PersonService) : PersonsApi {

    override fun getPersons(): List<Person> {
        return personService.getAllPeople().toList()
    }

    override fun getCareerPath(id: UUID?): MutableList<CareerItem> {
        TODO("Not yet implemented")
    }

    override fun getEducationPath(id: UUID?): MutableList<EducationItem> {
        TODO("Not yet implemented")
    }

    override fun getPersonalInformation(id: UUID?): PersonalInformation? {
        if (id == null) {
            throw NotFoundException("Person not found")
        }

        return personService.getPerson(id) ?: throw NotFoundException("Person not found")
    }
}
