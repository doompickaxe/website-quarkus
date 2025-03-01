package io.kay.website.api

import io.kay.website.mapper.PersonMapper
import io.kay.website.repositories.PersonRepo
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import java.util.*

class PersonsResource : PersonsApi() {

    @Inject
    private lateinit var personRepo: PersonRepo

    @Inject
    private lateinit var personMapper: PersonMapper

    override fun getPersons(): Response {
        return personRepo.getAllPeople()
            .map { personMapper.toApi(it) }
            .let {
                Response.ok(it).build()
            }
    }

    override fun getPersonalInformation(id: UUID?): Response {
        if (id == null) {
            return Response.noContent().build()
        }

        return personRepo.getPerson(id)?.let {
            Response.ok(personMapper.toApi(it)).build()
        } ?: Response.noContent().build()
    }
}
