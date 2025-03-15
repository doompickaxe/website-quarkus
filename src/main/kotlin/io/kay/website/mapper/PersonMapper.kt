package io.kay.website.mapper

import io.kay.website.api.model.Person
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface PersonMapper {

    @Mapping(target = "firstname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    @Mapping(target = "id", source = "uuid")
    fun toApi(person: io.kay.website.domain.Person): Person
}
