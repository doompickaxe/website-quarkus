package io.kay.website.config

import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import javax.sql.DataSource

@ApplicationScoped
@Startup
class DatabaseConfig(dataSource: DataSource) {

    init {
        TransactionManager.defaultDatabase = Database.connect(dataSource)
    }
}