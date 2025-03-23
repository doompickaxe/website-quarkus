package io.kay.website.mapper

import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.domain.City
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface PersonMapper {

    @Mapping(target = "id", source = "uuid")
    fun toApiPerson(person: io.kay.website.domain.Person): Person

    fun toApiPersons(person: Iterable<io.kay.website.domain.Person>): Iterable<Person>

    @Mapping(target = "age", expression = "java(person.getBirthday().until(java.time.LocalDate.now()).getYears())")
    @Mapping(target = "phoneNumber", source = "phone")
    fun toApiPersonalInformation(person: io.kay.website.domain.Person): PersonalInformation

    @Mapping(target = "city", source = "name")
    @Mapping(target = "country", source = "country.name")
    fun toApi(city: City): io.kay.website.api.model.City
}
