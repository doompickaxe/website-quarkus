package io.kay.website.repositories

import io.kay.website.domain.Career
import io.kay.website.domain.CareerTable
import io.kay.website.domain.Person
import io.kay.website.domain.PersonTable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.sql.DataSource

@ApplicationScoped
class CareerRepo(dataSource: DataSource) {

    init {
        TransactionManager.defaultDatabase = Database.connect(dataSource)
    }

    fun getCareerOfPerson(id: UUID): List<Career> {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)

            val person = Person.find { PersonTable.uuid eq id }.firstOrNull()
                ?: throw NotFoundException("Person with id $id not found")
            Career.find { CareerTable.person eq person.id }.toList()
        }
    }
}
