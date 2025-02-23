package io.kay.website.api;

import io.kay.website.api.model.PersonalInformation;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


import io.swagger.annotations.*;


import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;


@Api(description = "the personal API")
@Path("/personal")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class PersonalApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "General information and things to know about me", response = PersonalInformation.class, tags={ "personal" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Retrieved personal information", response = PersonalInformation.class),
        @ApiResponse(code = 500, message = "An unexpected error has occurred", response = Void.class)
    })
    public Response getPersonalInformation() {
        return Response.ok().entity("magic!").build();
    }
}
