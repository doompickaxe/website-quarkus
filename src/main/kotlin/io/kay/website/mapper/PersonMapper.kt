package io.kay.website.mapper

import io.kay.website.api.model.Person
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface PersonMapper {

    fun toApi(person: io.kay.website.domain.Person): Person
}
