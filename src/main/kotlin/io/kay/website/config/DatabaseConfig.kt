package io.kay.website.config

import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import javax.sql.DataSource

@ApplicationScoped
@Startup
class DatabaseConfig(dataSource: DataSource) {

    init {
        TransactionManager.defaultDatabase = Database.connect(dataSource)
    }
}
