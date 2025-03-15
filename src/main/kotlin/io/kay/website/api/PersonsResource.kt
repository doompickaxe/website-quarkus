package io.kay.website.api

import io.kay.website.api.model.CareerItem
import io.kay.website.api.model.EducationItem
import io.kay.website.api.model.Person
import io.kay.website.api.model.PersonalInformation
import io.kay.website.mapper.PersonMapper
import io.kay.website.repositories.PersonRepo
import jakarta.inject.Inject
import java.util.*

class PersonsResource : PersonsApi {

    @Inject
    private lateinit var personRepo: PersonRepo

    @Inject
    private lateinit var personMapper: PersonMapper

    override fun getPersons(): List<Person> {
        return personRepo.getAllPeople()
            .map { personMapper.toApi(it) }
    }

    override fun getCareerPath(id: UUID?): MutableList<CareerItem> {
        TODO("Not yet implemented")
    }

    override fun getEducationPath(id: UUID?): MutableList<EducationItem> {
        TODO("Not yet implemented")
    }

    override fun getPersonalInformation(id: UUID?): PersonalInformation? {
        TODO("Not yet implemented")
//        if (id == null) {
//            return null
//        }
//
//        return personRepo.getPerson(id)?.let {
//           personMapper.toApi(it)
//        }
    }
}
