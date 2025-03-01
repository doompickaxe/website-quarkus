package io.kay.website.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object CityTable : LongIdTable("city") {
    val name = text("name")
    val country = reference("country", CountryTable.id)
}

class City(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<City>(CityTable)

    var name by CityTable.name
    var country by CityTable.country
}
