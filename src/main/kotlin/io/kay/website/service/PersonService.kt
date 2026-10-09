package io.kay.website.service

import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.api.model.UpdatePersonalInformation
import io.kay.website.mapper.PersonMapper
import io.kay.website.repositories.PersonRepo
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

@ApplicationScoped
class PersonService(private val personRepo: PersonRepo, private val personMapper: PersonMapper) {

    fun getAllPeople(): Iterable<Person> {
        return transaction {
            personMapper.toApiPersons(personRepo.getAllPeople())
        }
    }

    fun getPerson(id: UUID): PersonalInformation? {
        return transaction {
            personRepo.getPerson(id)?.let { personMapper.toApiPersonalInformation(it) }
        }
    }

    fun updatePerson(id: UUID, update: UpdatePersonalInformation) = transaction {
        val person = personRepo.getPerson(id) ?: throw NotFoundException("Person not found")
        val updatedPerson = personRepo.updatePerson(person, update)
        personMapper.toApiPersonalInformation(updatedPerson)
    }
}
