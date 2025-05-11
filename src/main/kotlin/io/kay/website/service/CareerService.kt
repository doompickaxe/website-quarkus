package io.kay.website.service

import io.kay.website.api.model.CareerItem
import io.kay.website.mapper.CareerMapper
import io.kay.website.repositories.CareerRepo
import jakarta.enterprise.context.ApplicationScoped
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@ApplicationScoped
class CareerService(private val careerRepo: CareerRepo, private val careerMapper: CareerMapper) {

    fun getCareerOfPerson(id: UUID): Iterable<CareerItem> {
        return transaction {
            val career = careerRepo.getCareerOfPerson(id)
            careerMapper.toApiCareers(career)
        }
    }
}
