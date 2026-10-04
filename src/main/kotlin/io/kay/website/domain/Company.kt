package io.kay.website.domain

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.java.javaUUID
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass

object CompanyTable : LongIdTable("company") {
    val name = text("name")
    val branch = text("branch")
    val city = reference("city", CityTable)
    val amountOfEmployees = long("employee_amount")
    val uuid = javaUUID("uuid")
}

class Company(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Company>(CompanyTable)

    var name by CompanyTable.name
    var branch by CompanyTable.branch
    var city by City referencedOn CompanyTable.city
    var amountOfEmployees by CompanyTable.amountOfEmployees
    var uuid by CompanyTable.uuid
}
