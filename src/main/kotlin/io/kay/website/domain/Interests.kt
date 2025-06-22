package io.kay.website.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object InterestsTable : LongIdTable("interests") {
    val name = text("name")
}

class Interests(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Interests>(InterestsTable)

    var name by InterestsTable.name
}

object PersonInterestsTable : Table("person_has_interests") {
    val interests = reference("interests", InterestsTable)
    val person = reference("person", PersonTable, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(interests, person)
}