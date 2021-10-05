package com.hv.catalog.graphqlclientjava.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GraphqlClientUtils {

  /**
   * Get WebClient.
   *
   * @return webclient
   */
  public static WebClient getWebClient() {
    return WebClient
        .builder()
        .baseUrl("https://api.spacex.land/graphql/")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
