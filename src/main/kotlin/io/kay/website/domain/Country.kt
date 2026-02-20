package io.kay.website.domain

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass

object CountryTable : LongIdTable("country") {
    val name = text("name")
    val code = text("code")
}

class Country(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Country>(CountryTable)

    var name by CountryTable.name
    var code by CountryTable.code
}
