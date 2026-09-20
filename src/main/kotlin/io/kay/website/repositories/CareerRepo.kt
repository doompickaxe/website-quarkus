package io.kay.website.repositories

import io.kay.website.api.model.CareerItem
import io.kay.website.domain.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.jetbrains.exposed.v1.core.Slf4jSqlDebugLogger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*
import io.kay.website.api.model.Company as ApiCompany

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

    fun addNewCareerItemsOfPerson(personId: UUID, careerItems: List<CareerItem>) {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)

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

    fun createCompany(company: ApiCompany): Company {
        return transaction {
            addLogger(Slf4jSqlDebugLogger)
            val usedCity = City.find { CityTable.name eq company.city.city }.firstOrNull()
                ?: throw NotFoundException("City ${company.city} not found")

            Company.new {
                name = company.name
                branch = company.branch
                city = usedCity
                amountOfEmployees = company.amountOfEmployees
            }
        }
    }
}
