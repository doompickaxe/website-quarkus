package io.kay.website.domain

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.javatime.date

object CareerTable : LongIdTable("career") {
    val company = reference("company", CompanyTable)
    val jobTitle = text("job_title")
    val start = date("start_date")
    val end = date("end_date").nullable()
    val jobDescription = text("job_description")
    val tasks = text("tasks")
    val person = reference("person", PersonTable)
}

class Career(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Career>(CareerTable)

    var company by Company referencedOn CareerTable.company
    var jobTitle by CareerTable.jobTitle
    var start by CareerTable.start
    var end by CareerTable.end
    var jobDescription by CareerTable.jobDescription
    var tasks by CareerTable.tasks
    var person by Person referencedOn CareerTable.person
}
