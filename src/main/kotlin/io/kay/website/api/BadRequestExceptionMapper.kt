package io.kay.website.api

import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class BadRequestExceptionMapper : ExceptionMapper<BadRequestException> {
    override fun toResponse(exception: BadRequestException): Response {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(mapOf("message" to exception.message))
            .build()
    }
}
