package io.kay.website.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object CountryTable : LongIdTable("country") {
    val name = text("name")
    val code = text("code")
}

class Country(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Country>(CountryTable)

    var name by CountryTable.name
    var code by CountryTable.code
}
