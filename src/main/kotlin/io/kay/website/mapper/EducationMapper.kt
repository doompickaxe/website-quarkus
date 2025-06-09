package io.kay.website.mapper

import io.kay.website.api.model.EducationItem
import io.kay.website.domain.Education
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = [PersonMapper::class])
interface EducationMapper {

    fun toApiEducation(education: Education): EducationItem

    fun toApiEducations(career: Iterable<Education>): Iterable<EducationItem>
}
