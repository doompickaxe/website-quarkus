package io.kay.website.repositories

import io.kay.website.domain.Education
import io.kay.website.domain.EducationTable
import io.kay.website.domain.Person
import io.kay.website.domain.PersonTable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

@ApplicationScoped
class EducationRepo {

    fun getEducationOfPerson(id: UUID): List<Education> {
        return transaction {
            val person = Person.find { PersonTable.uuid eq id }.firstOrNull()
                ?: throw NotFoundException("Person with id $id not found")
            Education.find { EducationTable.person eq person.id }.toList()
        }
    }
}
