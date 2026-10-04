package io.kay.website.mapper

import io.kay.website.api.model.CareerItem
import io.kay.website.domain.Career
import io.kay.website.domain.Company
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import io.kay.website.api.model.Company as ApiCompany

@Mapper(
    componentModel = MappingConstants.ComponentModel.CDI,
    uses = [PersonMapper::class],
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface CareerMapper {

    fun toApiCareer(career: Career): CareerItem
    fun toApiCareers(career: Iterable<Career>): Iterable<CareerItem>

    @Mapping(target = "id", source = "uuid")
    fun toApiCompany(company: Company): ApiCompany
}
