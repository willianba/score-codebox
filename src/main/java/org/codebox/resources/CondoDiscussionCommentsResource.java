package org.codebox.resources;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.codebox.entities.CondosDiscussionComment;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.vertx.axle.pgclient.PgPool;

@ApplicationScoped
@Path("condos/discussion_comments")
@Produces(MediaType.APPLICATION_JSON)
public class CondoDiscussionCommentsResource {
  @Inject PgPool client;

  @GET
  public CompletionStage<Response> getAll() {
    return CondosDiscussionComment.findAll(client)
      .thenApply(Response::ok)
      .thenApply(ResponseBuilder::build);
  }

  @GET
  @Path("{id}")
  public CompletionStage<Response> getSingle(@PathParam String id) {
    return CondosDiscussionComment.findById(client, id)
      .thenApply(condo -> condo != null ? Response.ok(condo) : Response.status(Status.NOT_FOUND))
      .thenApply(ResponseBuilder::build);
  }

  @PUT
  public CompletionStage<Response> change(CondosDiscussionComment condoDiscussion) {
    return CondosDiscussionComment.update(client, condoDiscussion)
    .thenApply(updatedCondo -> updatedCondo != null ? Response.ok(updatedCondo) : Response.status(Status.NOT_FOUND))
    .thenApply(ResponseBuilder::build);
  }
}
