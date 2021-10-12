package com.hv.catalog.graphqlclientjava.service;

import com.hv.catalog.graphql.generated.client.UsersGraphQLQuery;
import com.hv.catalog.graphql.generated.client.UsersProjectionRoot;
import com.hv.catalog.graphql.generated.types.MyUser;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private WebClient webClient;
  private WebClientGraphQLClient client;

  private WebClient getWebClient() {
    if (webClient == null) {
      webClient = GraphqlClientUtils.getWebClient();
    }
    return webClient;
  }

  private WebClientGraphQLClient getClient() {
    if (client == null) {
      client = MonoGraphQLClient.createWithWebClient(getWebClient());
    }
    return client;
  }

  /**
   * Get users.
   *
   * @return list
   */
  public List<MyUser> getUsers() {
    List<MyUser> userList = Collections.emptyList();
    WebClientGraphQLClient client = getClient();
    UsersGraphQLQuery query = UsersGraphQLQuery.newRequest().build();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query,
        new UsersProjectionRoot().id().name().rocket().timestamp().twitter());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    if (response != null) {
      userList = response.extractValueAsObject(query.getOperationName(),
          new TypeRef<List<MyUser>>() {
          });
    }
    return userList;
  }

  public MyUser getUserByName(String name) {
    MyUser user = null;
    WebClientGraphQLClient client = getClient();
    UsersGraphQLQuery query = UsersGraphQLQuery.newRequest().build();
    GraphQLQueryRequest request = new GraphQLQueryRequest(query, new UsersProjectionRoot().id());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    if (response != null) {
      user = response.extractValueAsObject(query.getOperationName(),
          new TypeRef<MyUser>() {
          });
    }
    return user;
  }

}
