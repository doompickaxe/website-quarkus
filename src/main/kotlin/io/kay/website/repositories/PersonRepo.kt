package io.kay.website.repositories

import io.kay.website.api.model.UpdatePersonalInformation
import io.kay.website.domain.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

@ApplicationScoped
class PersonRepo {

    fun getAllPeople(): Iterable<Person> {
        return transaction {
            Person.all()
        }
    }

    fun getPerson(id: UUID): Person? {
        return transaction {
            Person.find { PersonTable.uuid eq id }.firstOrNull()
        }
    }

    fun updatePerson(person: Person, update: UpdatePersonalInformation) = transaction {
        val currentCity = City.find { CityTable.name eq update.currentlyLivingIn.city }.firstOrNull()
            ?: throw NotFoundException("City ${update.currentlyLivingIn.city} not found")

        val bornCity = City.find { CityTable.name eq update.originalFrom.city }.firstOrNull()

        val languages = update.languages
            .map { Language.find { LanguageTable.name eq it }.firstOrNull() ?: createMissingLanguage(it) }

        val interests = update.interests
            .map { Interests.find { InterestsTable.name eq it }.firstOrNull() ?: createMissingInterest(it) }

        person.apply {
            firstName = update.firstName
            lastName = update.lastName
            birthday = update.birthday
            email = update.email
            phone = update.phoneNumber
            bornCity?.let { originalFrom = it }
            currentlyLivingIn = currentCity
        }

        PersonLanguageTable.deleteWhere { PersonLanguageTable.person eq person.id }
        person.languages = SizedCollection(languages)
        PersonInterestsTable.deleteWhere { PersonInterestsTable.person eq person.id }
        person.interests = SizedCollection(interests)
        person
    }

    fun createMissingLanguage(language: String) = transaction {
        Language.new { name = language }
    }

    fun createMissingInterest(interest: String) = transaction {
        Interests.new { name = interest }
    }
}
