package io.kay.website.service

import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.mapper.PersonMapper
import io.kay.website.repositories.PersonRepo
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@ApplicationScoped
class PersonService(private val personRepo: PersonRepo, private val personMapper: PersonMapper) {

    fun getAllPeople(): Iterable<Person> {
        return personMapper.toApiPersons(personRepo.getAllPeople())
    }

    fun getPerson(id: UUID): PersonalInformation? {
        return transaction {
            personRepo.getPerson(id)?.let { personMapper.toApiPersonalInformation(it) }
        }
    }
}
