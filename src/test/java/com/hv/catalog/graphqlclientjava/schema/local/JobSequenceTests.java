package com.hv.catalog.graphqlclientjava.schema.local;

import com.hv.catalog.graphql.generated.client.JobSequenceCreateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobSequenceCreateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.JobSequenceCreateOne_RecordProjection;
import com.hv.catalog.graphql.generated.client.JobSequenceFindByIdGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobSequenceFindByIdProjectionRoot;
import com.hv.catalog.graphql.generated.types.CreateOneJobSequenceInput;
import com.hv.catalog.graphql.generated.types.CreateOneJobSequencePayload;
import com.hv.catalog.graphql.generated.types.JobSequence;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.Assert;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Disabled("Should run against local app-server")
public class JobSequenceTests {

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
  @Disabled
  void jobSequenceCreateOne() {
    CreateOneJobSequenceInput input = CreateOneJobSequenceInput
        .newBuilder()
        .name("jsTest2")
        .build();
    JobSequenceCreateOneGraphQLQuery query =
        JobSequenceCreateOneGraphQLQuery
            .newRequest()
            .record(input)
            .build();
    JobSequenceCreateOne_RecordProjection projection =
        new JobSequenceCreateOneProjectionRoot().record()._id().name();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    CreateOneJobSequencePayload responseObject =
        response.extractValueAsObject(query.getOperationName(),
            new TypeRef<CreateOneJobSequencePayload>() {
            });
    JobSequence record = responseObject.getRecord();
    Assertions.assertNotNull(record);
  }

  @Test
  void jobSequenceFindById() {
    String objectIdString = "616d499afd4d548daa4fbb7f";
    JobSequenceFindByIdGraphQLQuery query = JobSequenceFindByIdGraphQLQuery
        .newRequest()
        ._id(objectIdString)
        .build();
    JobSequenceFindByIdProjectionRoot projection =
        new JobSequenceFindByIdProjectionRoot()._id();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    JobSequence jobSequence =
        response.extractValueAsObject(query.getOperationName(), new TypeRef<JobSequence>() {
        });
    Assertions.assertEquals(objectIdString, jobSequence.get_id());
  }

}
