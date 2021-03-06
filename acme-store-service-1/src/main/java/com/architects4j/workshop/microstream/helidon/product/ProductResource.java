package com.architects4j.workshop.microstream.helidon.product;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@RequestScoped
@Path("products")
public class ProductResource {

    private Inventory repository;

    /**
     * @Deprecated CDI only
     */
    ProductResource() {
    }

    @Inject
    ProductResource(Inventory repository) {
        this.repository = repository;
    }

    //TODO don't worried about pagination
    @GET
    @Operation(summary = "Get all products", description = "Returns all available products")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @APIResponse(responseCode = "200", description = "The items")
    @Tag(name = "BETA", description = "This API is currently in beta state")
    @APIResponse(description = "The items",
            responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Collection.class,
                            readOnly = true, description = "the items",
                            required = true, name = "items")))
    public Collection<Product> getAll() {
        return repository.getAll();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Find an product by id", description = "Find an item by id")
    @APIResponse(responseCode = "200", description = "The item")
    @APIResponse(responseCode = "404", description = "When the id does not exist")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Tag(name = "BETA", description = "This API is currently in beta state")
    @APIResponse(description = "The Item",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Product.class)))
    public Product findById(@Parameter(description = "The product's name", required = true, example = "water",
            schema = @Schema(type = SchemaType.STRING))
                         @PathParam("id") String id) {
        return this.repository.findById(id).orElseThrow(
                () -> new WebApplicationException("There is no item with the id " + id, Response.Status.NOT_FOUND));
    }

    @POST
    @Operation(summary = "Insert a product", description = "Insert a product")
    @APIResponse(responseCode = "201", description = "When creates a product")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Tag(name = "BETA", description = "This API is currently in beta state")
    public Response insert(@RequestBody(description = "Create a new product.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))) @Valid Product item) {
        return Response.status(Response.Status.CREATED)
                .entity(repository.save(item))
                .build();
    }

    @PUT
    @Path("{id}")
    public Product update(@PathParam("id") String id, @Valid Product item) {
        Product databaseItem = repository.findById(id)
                .orElseThrow(
                        () -> new WebApplicationException("There is no item with the id " + id, Response.Status.NOT_FOUND));
        repository.save(item);
        return item;
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete an item by ID", description = "Delete an item by ID")
    @APIResponse(responseCode = "200", description = "When deletes the item")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Tag(name = "BETA", description = "This API is currently in beta state")
    public Response delete(@PathParam("id") String id) {
        this.repository.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
