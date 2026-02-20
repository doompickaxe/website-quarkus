package io.kay.website.repositories

import io.kay.website.domain.Career
import io.kay.website.domain.CareerTable
import io.kay.website.domain.Person
import io.kay.website.domain.PersonTable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

@ApplicationScoped
class CareerRepo {

    fun getCareerOfPerson(id: UUID): List<Career> {
        return transaction {
            val person = Person.find { PersonTable.uuid eq id }.firstOrNull()
                ?: throw NotFoundException("Person with id $id not found")
            Career.find { CareerTable.person eq person.id }.toList()
        }
    }
}
