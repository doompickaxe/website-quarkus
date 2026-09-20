package io.kay.website.util

import io.kay.website.domain.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun clearDB(db: Database) {
    transaction(db) {
        listOf(
            EducationTable,
            CareerTable,
            CompanyTable,
            PersonTable,
            CityTable,
            CountryTable,
            LanguageTable,
            InterestsTable
        ).forEach {
            it.deleteAll()
        }
    }
}
