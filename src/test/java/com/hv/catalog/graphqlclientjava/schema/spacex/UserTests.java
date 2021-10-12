package com.hv.catalog.graphqlclientjava.schema.spacex;

import com.hv.catalog.graphql.generated.client.Update_usersGraphQLQuery;
import com.hv.catalog.graphql.generated.client.Update_usersProjectionRoot;
import com.hv.catalog.graphql.generated.client.Update_users_ReturningProjection;
import com.hv.catalog.graphql.generated.client.UsersGraphQLQuery;
import com.hv.catalog.graphql.generated.client.UsersProjectionRoot;
import com.hv.catalog.graphql.generated.types.User;
import com.hv.catalog.graphql.generated.types.users;
import com.hv.catalog.graphql.generated.types.users_bool_exp;
import com.hv.catalog.graphql.generated.types.users_mutation_response;
import com.hv.catalog.graphql.generated.types.users_set_input;
import com.hv.catalog.graphql.generated.types.uuid_comparison_exp;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.scalar.GraphqlIDCoercing;
import graphql.schema.Coercing;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserTests {
  private WebClient webClient;
  private WebClientGraphQLClient client;

  @BeforeEach
  void setUp() {
    if (null == webClient) {
      webClient = GraphqlClientUtils.getWebClient();
    }
    if (null == client) {
      client = MonoGraphQLClient.createWithWebClient(webClient);
    }
  }

  @Test
  void uuidTest() {
    String uuidString = "0ac58753-a650-4343-83f7-201f4fd72c70";
    UUID uuid = UUID.fromString(uuidString);
    Assertions.assertEquals(uuid.toString(), uuidString);
  }

  @Test
  void getUserById() {
    UUID uuid = UUID.fromString("0ac58753-a650-4343-83f7-201f4fd72c70");
    uuid_comparison_exp id = uuid_comparison_exp.newBuilder()._eq(uuid).build();
    users_bool_exp usersBoolExp = users_bool_exp.newBuilder().id(id).build();
    UsersGraphQLQuery query = UsersGraphQLQuery.newRequest().where(usersBoolExp).build();
    Map<Class<?>, Coercing<?, ?>> scalars = new HashMap<>();
    scalars.put(java.util.UUID.class, new GraphqlIDCoercing());
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query,
            new UsersProjectionRoot()
                .id().name().twitter().timestamp().rocket(),
            scalars);
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    List<users> usersList = response.extractValueAsObject(query.getOperationName(),
        new TypeRef<List<users>>() {
        });
    Assertions.assertNotNull(response);
    Assertions.assertFalse(usersList.isEmpty());
    Assertions.assertTrue(usersList.stream().anyMatch(user -> user.getId().equals(uuid)));
  }

  @Test
  @DisplayName("Mutation update_users")
  void userUpdateById() {
    String userId = "0ac58753-a650-4343-83f7-201f4fd72c70";
    String userName = "Elanthamil";

    // client
    WebClient webClient = GraphqlClientUtils.getWebClient();
    WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(webClient);

    // query
    UUID uuid = UUID.fromString(userId);
    uuid_comparison_exp id = uuid_comparison_exp.newBuilder()._eq(uuid).build();
    users_bool_exp usersBoolExp = users_bool_exp.newBuilder().id(id).build();
    users_set_input usersSetInput = users_set_input
        .newBuilder()
        .name(userName)
        .build();
    Update_usersGraphQLQuery query = Update_usersGraphQLQuery
        .newRequest()
        .where(usersBoolExp)
        ._set(usersSetInput)
        .build();
    Update_users_ReturningProjection projections = new Update_usersProjectionRoot()
        .returning().id().name().rocket().timestamp().twitter();
    Map<Class<?>, Coercing<?, ?>> scalars = new HashMap<>();
    scalars.put(java.util.UUID.class, new GraphqlIDCoercing());

    // request
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, projections, scalars);

    // execute request and get response
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    users_mutation_response user = response.extractValueAsObject(query.getOperationName(),
        new TypeRef<users_mutation_response>() {
        });
    List<users> users = user.getReturning();
    Assertions.assertNotNull(user);
    Assertions.assertTrue(users.stream().anyMatch(user1 -> user1.getId().toString().equals(userId)));
    Assertions.assertTrue(users.stream().anyMatch(user1 -> user1.getName().equals(userName)));
  }
}
