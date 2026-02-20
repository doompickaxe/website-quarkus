package io.kay.website.domain

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.javatime.date

object EducationTable : LongIdTable("education") {
    val schoolName = text("school_name")
    val start = date("start_date")
    val end = date("end_date").nullable()
    val degree = text("degree")
    val description = text("description")
    val educationType = enumerationByName<EducationType>("education_type", 15)
    val person = reference("person", PersonTable)
    val city = reference("city", CityTable)
}

class Education(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Education>(EducationTable)

    var schoolName by EducationTable.schoolName
    var start by EducationTable.start
    var end by EducationTable.end
    var degree by EducationTable.degree
    var description by EducationTable.description
    var educationType by EducationTable.educationType
    var person by Person referencedOn EducationTable.person
    var city by City referencedOn EducationTable.city
}

enum class EducationType {
    ELEMENTARY_SCHOOL,
    SECONDARY_SCHOOL,
    COLLEGE,
    UNIVERSITY
}
