package io.kay.website.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object PersonTable : LongIdTable("person") {
    val uuid = uuid("uuid").autoGenerate().uniqueIndex()
    val firstName = text("first_name")
    val lastName = text("last_name")
    val birthday = date("birthday")
    val email = text("email")
    val phone = text("phone")
    val originalFrom = reference("original_from", CityTable)
    val currentlyIn = optReference("currently_in", CityTable)
}

class Person(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Person>(PersonTable)

    var uuid by PersonTable.uuid
    var firstName by PersonTable.firstName
    var lastName by PersonTable.lastName
    var birthday by PersonTable.birthday
    var email by PersonTable.email
    var phone by PersonTable.phone
    var originalFrom by City referencedOn PersonTable.originalFrom
    var currentlyLivingIn by City optionalReferencedOn PersonTable.currentlyIn
}
