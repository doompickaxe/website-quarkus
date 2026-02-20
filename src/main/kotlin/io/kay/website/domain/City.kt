package io.kay.website.domain

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass

object CityTable : LongIdTable("city") {
    val name = text("name")
    val country = reference("country", CountryTable)
}

class City(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<City>(CityTable)

    var name by CityTable.name
    var country by Country referencedOn CityTable.country
}
