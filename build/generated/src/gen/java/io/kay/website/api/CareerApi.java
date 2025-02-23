package io.kay.website.api;

import io.kay.website.api.model.CareerItem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


import io.swagger.annotations.*;


import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;


@Api(description = "the career API")
@Path("/career")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class CareerApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "The career path until today", response = CareerItem.class, responseContainer = "List", tags={ "career" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Career path until today", response = CareerItem.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "An unexpected error has occurred", response = Void.class)
    })
    public Response getCareerPath() {
        return Response.ok().entity("magic!").build();
    }
}
