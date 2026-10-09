package io.kay.website.api

import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.api.model.UpdatePersonalInformation
import io.kay.website.service.PersonService
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.util.*

class PersonsResource(
    private val personService: PersonService,
) : PersonsApi {

    override fun getPersons(): List<Person> {
        return personService.getAllPeople().toList()
    }

    override fun updatePersonalInformation(id: UUID?, update: UpdatePersonalInformation?): PersonalInformation {
        if (id == null) {
            throw NotFoundException("Person not found")
        }
        if (update == null) {
            throw BadRequestException("No body was given")
        }

        return personService.updatePerson(id, update)
    }

    override fun getPersonalInformation(id: UUID?): PersonalInformation {
        if (id == null) {
            throw NotFoundException("Person not found")
        }

        return personService.getPerson(id) ?: throw NotFoundException("Person not found")
    }
}
