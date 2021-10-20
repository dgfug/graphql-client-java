package com.hv.catalog.graphqlclientjava.schema.local;

import com.hv.catalog.graphql.generated.client.DataEntitiesCreateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DataEntitiesCreateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.DataEntitiesCreateOne_RecordProjection;
import com.hv.catalog.graphql.generated.types.CreateOneDataEntitiesInput;
import com.hv.catalog.graphql.generated.types.CreateOneDataEntitiesPayload;
import com.hv.catalog.graphql.generated.types.DataEntities;
import com.hv.catalog.graphql.generated.types.DataEntitiesBeAttributesInput;
import com.hv.catalog.graphql.generated.types.EnumDataEntitiesType;
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

@Disabled("Local app-server should be up.")
public class DataEntitiesTest {
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
  @DisplayName("Mutation: DataEntitiesCreateOne")
  @Disabled("Creation failing at server end")
  void dataEntitiesCreateOne() {
    // valid resourceOrigin: 616c711093050227a9bef31e
    ObjectId test = new ObjectId("resourceOrigin");
    String resourceOrigin = test.toHexString();
    CreateOneDataEntitiesInput input = CreateOneDataEntitiesInput
        .newBuilder()
        .name("testName")
        .type(EnumDataEntitiesType.File)
        .path("/testPath")
        .beAttributes(DataEntitiesBeAttributesInput
            .newBuilder()
            .resourceOrigin("616c711093050227a9bef31e")
            .build())
        .build();
    DataEntitiesCreateOneGraphQLQuery query =
        DataEntitiesCreateOneGraphQLQuery
            .newRequest()
            .record(input)
            .build();
    DataEntitiesCreateOne_RecordProjection projection =
        new DataEntitiesCreateOneProjectionRoot()
            .record()._id().name().path();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    Assertions.assertNotNull(responseMono);
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    CreateOneDataEntitiesPayload responseObject =
        response.extractValueAsObject(query.getOperationName(),
            new TypeRef<CreateOneDataEntitiesPayload>() {
            });
    DataEntities dataEntity = responseObject.getRecord();
    Assertions.assertNotNull(dataEntity);
    Assertions.assertNotNull(dataEntity.get_id());
    Assertions.assertNotNull(dataEntity.getName());
    Assertions.assertNotNull(dataEntity.getPath());
  }
}
