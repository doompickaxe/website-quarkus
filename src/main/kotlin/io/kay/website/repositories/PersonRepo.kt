package io.kay.website.repositories

import io.kay.website.domain.Person
import io.kay.website.domain.PersonTable
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.v1.core.eq
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
}
