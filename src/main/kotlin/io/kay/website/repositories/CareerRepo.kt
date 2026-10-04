package io.kay.website.repositories

import io.kay.website.api.model.UpdateCareerItem
import io.kay.website.api.model.UpsertCompany
import io.kay.website.domain.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class CareerRepo {

    fun getCareerOfPerson(id: UUID): List<Career> {
        return transaction {
            val person = Person.find { PersonTable.uuid eq id }.firstOrNull()
                ?: throw NotFoundException("Person with id $id not found")
            Career.find { CareerTable.person eq person.id }.toList()
        }
    }

    fun clearCareerOfPerson(personId: UUID) {
        return transaction {
            val person = Person.find { PersonTable.uuid eq personId }.firstOrNull()
                ?: throw NotFoundException("Person with id $personId not found")
            Career.find { CareerTable.person eq person.id }.forEach { it.delete() }
        }
    }

    fun addNewCareerItemsOfPerson(personId: UUID, careerItems: List<UpdateCareerItem>) {
        return transaction {
            val personToUse = Person.find { PersonTable.uuid eq personId }.firstOrNull()
                ?: throw NotFoundException("Person with id $personId not found")

            careerItems.forEach { career ->
                val usedCompany = Company.find { CompanyTable.name eq career.company.name }.firstOrNull()
                    ?: throw NotFoundException("Company ${career.company} not found")

                Career.new {
                    company = usedCompany
                    start = career.start
                    end = career.end
                    jobTitle = career.jobTitle
                    jobDescription = career.jobDescription
                    tasks = career.tasks
                    person = personToUse
                }
            }
        }
    }

    fun findCompanyByName(name: String): Company? {
        return transaction {
            Company.find { CompanyTable.name eq name }.firstOrNull()
        }
    }

    fun createCompany(company: UpsertCompany): Company {
        return transaction {
            val usedCity = City.find { CityTable.name eq company.city.city }.firstOrNull()
                ?: throw NotFoundException("City ${company.city} not found")

            Company.new {
                name = company.name
                branch = company.branch
                city = usedCity
                amountOfEmployees = company.amountOfEmployees
                uuid = Uuid.generateV7().toJavaUuid()
            }
        }
    }

    fun searchCompanyByName(name: String?): List<Company> {
        return transaction {
            Company.find { CompanyTable.name like "%${name ?: ""}%" }.toList()
        }
    }

    fun findCompanyByUUID(id: UUID): Company? {
        return transaction {
            Company.find { CompanyTable.uuid eq id }.firstOrNull()
        }
    }

    fun updateCompany(id: UUID, upsertCompany: UpsertCompany): Company {
        return transaction {
            val company = findCompanyByUUID(id) ?: throw NotFoundException("Company with id $id not found")

            val usedCity = City.find { CityTable.name eq upsertCompany.city.city }.firstOrNull()
                ?: throw NotFoundException("City ${company.city} not found")

            company.apply {
                name = upsertCompany.name
                branch = upsertCompany.branch
                city = usedCity
                amountOfEmployees = upsertCompany.amountOfEmployees
            }
        }
    }
}
