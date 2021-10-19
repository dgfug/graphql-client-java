package com.hv.catalog.graphqlclientjava.schema.local;

import com.hv.catalog.graphql.generated.client.JobInstanceCreateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobInstanceCreateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.JobInstanceCreateOne_RecordProjection;
import com.hv.catalog.graphql.generated.client.JobInstanceUpdateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobInstanceUpdateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.JobInstanceUpdateOne_RecordProjection;
import com.hv.catalog.graphql.generated.client.JobSequenceFindByIdGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobSequenceFindByIdProjectionRoot;
import com.hv.catalog.graphql.generated.types.CreateOneJobInstanceInput;
import com.hv.catalog.graphql.generated.types.CreateOneJobInstancePayload;
import com.hv.catalog.graphql.generated.types.FilterUpdateOneJobInstanceInput;
import com.hv.catalog.graphql.generated.types.JobInstance;
import com.hv.catalog.graphql.generated.types.JobSequence;
import com.hv.catalog.graphql.generated.types.UpdateOneJobInstanceInput;
import com.hv.catalog.graphql.generated.types.UpdateOneJobInstancePayload;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Disabled("Should run against local app-server")
public class JobInstanceTests {

  private static final String NAME = "testJobInstance";
  private static final String NAME_UPDATE = "testJobInstance-Update";

  private WebClient webClient;
  private WebClientGraphQLClient client;

  @BeforeEach
  void setUp() {
    if (null == webClient) {
      webClient = GraphqlClientUtils.getLocalWebClient();
    }
    if (null == client) {
      client = MonoGraphQLClient.createWithWebClient(webClient);
    }
  }


  @Test
  @DisplayName("Mutation: jobInstanceCreateOne")
//  @Disabled
  void jobInstanceCreateOne() {
    String jsId = "616d499afd4d548daa4fbb7f";
    JobSequenceFindByIdGraphQLQuery query1 = JobSequenceFindByIdGraphQLQuery
        .newRequest()
        ._id(jsId)
        .build();
    JobSequenceFindByIdProjectionRoot projection1 =
        new JobSequenceFindByIdProjectionRoot()._id();
    GraphQLQueryRequest request1 = new GraphQLQueryRequest(query1, projection1);
    Mono<GraphQLResponse> responseMono1 = client.reactiveExecuteQuery(request1.serialize());
    GraphQLResponse response1 = responseMono1.block();
    Assertions.assertNotNull(response1);
    JobSequence jobSequence =
        response1.extractValueAsObject(query1.getOperationName(), new TypeRef<JobSequence>() {
        });

    CreateOneJobInstanceInput input =
        CreateOneJobInstanceInput.newBuilder()
            .name("jiWithJs")
            .jobSequenceId(jobSequence.get_id())
            .build();
    JobInstanceCreateOneGraphQLQuery query = JobInstanceCreateOneGraphQLQuery
        .newRequest()
        .record(input)
        .build();
    JobInstanceCreateOne_RecordProjection projection =
        new JobInstanceCreateOneProjectionRoot().record()._id()
            .name().jobInstanceType().getParent()
            .jobSequenceId();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    CreateOneJobInstancePayload createOneJobInstancePayload =
        response.extractValueAsObject(query.getOperationName(),
            new TypeRef<CreateOneJobInstancePayload>() {
            });
    JobInstance record = createOneJobInstancePayload.getRecord();
//    Assertions.assertEquals(NAME, record.getName());
  }

  @Test
  @DisplayName("Mutation: jobInstanceUpdateOne")
//  @Disabled
  void jobInstanceUpdateOne() {
    FilterUpdateOneJobInstanceInput filter =
        FilterUpdateOneJobInstanceInput.newBuilder().name("DoesNotExist").build();
    UpdateOneJobInstanceInput input = UpdateOneJobInstanceInput
        .newBuilder()
        .name("NewlyUpdated")
        .build();
    JobInstanceUpdateOneGraphQLQuery query = JobInstanceUpdateOneGraphQLQuery
        .newRequest()
        .filter(filter)
        .record(input)
        .build();
    JobInstanceUpdateOne_RecordProjection projection =
        new JobInstanceUpdateOneProjectionRoot().record()._id().name();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
//    UpdateOneJobInstancePayload responseObject =
//        response.extractValueAsObject(query.getOperationName(),
//            new TypeRef<UpdateOneJobInstancePayload>() {
//            });
//    Assertions.assertNotNull(responseObject);
//    JobInstance record = responseObject.getRecord();
//    Assertions.assertEquals(NAME_UPDATE, record.getName());
  }

}
