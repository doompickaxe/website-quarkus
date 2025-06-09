package io.kay.website.repositories

import io.kay.website.domain.Education
import io.kay.website.domain.EducationTable
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
class EducationRepo(dataSource: DataSource) {

    init {
        TransactionManager.defaultDatabase = Database.connect(dataSource)
    }

    fun getEducationOfPerson(id: UUID): List<Education> {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)

            val person = Person.find { PersonTable.uuid eq id }.firstOrNull()
                ?: throw NotFoundException("Person with id $id not found")
            Education.find { EducationTable.person eq person.id }.toList()
        }
    }
}
