package io.kay.website.mapper

import io.kay.website.api.model.CareerItem
import io.kay.website.domain.Career
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = [PersonMapper::class])
interface CareerMapper {

    fun toApiCareer(career: Career): CareerItem

    fun toApiCareers(career: Iterable<Career>): Iterable<CareerItem>
}
