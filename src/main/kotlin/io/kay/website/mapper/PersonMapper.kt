package io.kay.website.mapper

import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.domain.City
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import io.kay.website.domain.Person as DomainPerson

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface PersonMapper {

    @Mapping(target = "id", source = "uuid")
    fun toApiPerson(person: DomainPerson): Person

    fun toApiPersons(person: Iterable<DomainPerson>): Iterable<Person>

    @Mapping(target = "age", expression = "java(person.getBirthday().until(java.time.LocalDate.now()).getYears())")
    @Mapping(target = "phoneNumber", source = "phone")
    fun toApiPersonalInformation(person: DomainPerson): PersonalInformation

    @Mapping(target = "city", source = "name")
    @Mapping(target = "country", source = "country.name")
    fun toApi(city: City): io.kay.website.api.model.City
}
