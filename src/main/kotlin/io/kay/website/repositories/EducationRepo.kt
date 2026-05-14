package io.kay.website.repositories

import io.kay.website.api.model.EducationItem
import io.kay.website.domain.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.Slf4jSqlDebugLogger
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

@ApplicationScoped
class EducationRepo {

    fun getEducationOfPerson(personId: UUID): List<Education> {
        return transaction {
            val person = Person.find { PersonTable.uuid eq personId }.firstOrNull()
                ?: throw NotFoundException("Person with id $personId not found")
            Education.find { EducationTable.person eq person.id }.toList()
        }
    }

    fun clearEducationOfPerson(personId: UUID) {
        return transaction {
            val person = Person.find { PersonTable.uuid eq personId }.firstOrNull()
                ?: throw NotFoundException("Person with id $personId not found")
            Education.find { EducationTable.person eq person.id }.forEach { it.delete() }
        }
    }

    fun addNewEducationItemsOfPerson(personId: UUID, educationItems: List<EducationItem>) {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)

            val personToUse = Person.find { PersonTable.uuid eq personId }.firstOrNull()
                ?: throw NotFoundException("Person with id $personId not found")

            educationItems.forEach { education ->
                val country = Country.find { CountryTable.name eq education.city.country }.firstOrNull()
                    ?: throw NotFoundException("County ${education.city.country} not found")

                val cityToUse = City.find {
                    (CityTable.country eq country.id) and (CityTable.name eq education.city.city)
                }
                    .firstOrNull() ?: throw NotFoundException("City ${education.city.city} not found")

                Education.new {
                    schoolName = education.schoolName
                    start = education.start
                    end = education.end
                    degree = education.degree
                    description = education.description
                    educationType = EducationType.valueOf(education.educationType.value())
                    person = personToUse
                    city = cityToUse
                }
            }
        }
    }
}
