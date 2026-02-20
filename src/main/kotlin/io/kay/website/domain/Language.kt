package io.kay.website.domain

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass

object LanguageTable : LongIdTable("languages") {
    val name = text("name")
}

class Language(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Language>(LanguageTable)

    var name by LanguageTable.name
}

object PersonLanguageTable : Table("person_speaks_language") {
    val language = reference("language", LanguageTable)
    val person = reference("person", PersonTable, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(language, person)
}
