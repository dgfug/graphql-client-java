package com.hv.catalog.graphqlclientjava.service;

import com.hv.catalog.graphql.generated.client.HistoriesGraphQLQuery;
import com.hv.catalog.graphql.generated.client.HistoriesProjectionRoot;
import com.hv.catalog.graphql.generated.types.Histories;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HistoryService {

  public List<Histories> getHistoryList() {
    List<Histories> histories = Collections.emptyList();
    WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(GraphqlClientUtils.getWebClient());
    HistoriesGraphQLQuery query = HistoriesGraphQLQuery.newRequest().build();
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(
        new GraphQLQueryRequest(query,
            new HistoriesProjectionRoot().title().details())
            .serialize());
    GraphQLResponse response = responseMono.block();
    if (response != null) {
      histories =
          response.extractValueAsObject(query.getOperationName(),
              new TypeRef<List<Histories>>() {
              });
    }
    return histories;
  }
}
