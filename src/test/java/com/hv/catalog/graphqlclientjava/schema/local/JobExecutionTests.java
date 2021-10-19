package com.hv.catalog.graphqlclientjava.schema.local;

import com.hv.catalog.graphql.generated.client.JobExecutionCreateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.JobExecutionCreateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.JobExecutionCreateOne_RecordProjection;
import com.hv.catalog.graphql.generated.types.CreateOneJobExecutionInput;
import com.hv.catalog.graphql.generated.types.EnumJobExecutionSparkJobType;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Disabled("Should run against local app-server")
public class JobExecutionTests {
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
  void jobExecutionCreateOne() {
    CreateOneJobExecutionInput input = CreateOneJobExecutionInput
        .newBuilder()
        .jobInstanceId("616d3e10fd4d548daa4fbb7c")
        .build();
    JobExecutionCreateOneGraphQLQuery query =
        JobExecutionCreateOneGraphQLQuery
            .newRequest()
            .record(input)
            .build();
    JobExecutionCreateOne_RecordProjection projection =
        new JobExecutionCreateOneProjectionRoot().record()._id();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
  }
}
