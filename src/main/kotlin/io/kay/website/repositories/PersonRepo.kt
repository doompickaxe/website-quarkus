package io.kay.website.repositories

import io.kay.website.domain.Person
import io.kay.website.domain.PersonTable
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.sql.DataSource

@ApplicationScoped
class PersonRepo(dataSource: DataSource) {

    init {
        TransactionManager.defaultDatabase = Database.connect(dataSource)
    }

    fun getAllPeople(): Iterable<Person> {
        return transaction {
            Person.all().toList()
        }
    }

    fun getPerson(id: UUID): Person? {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)

            Person.find { PersonTable.uuid eq id }.firstOrNull()
        }
    }
}
