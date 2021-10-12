package com.hv.catalog.graphqlclientjava.schema.local;

import com.hv.catalog.graphql.generated.client.DatasourceByIdGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DatasourceByIdProjectionRoot;
import com.hv.catalog.graphql.generated.client.DatasourceCreateOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DatasourceCreateOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.DatasourceRemoveByIdGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DatasourceRemoveByIdProjectionRoot;
import com.hv.catalog.graphql.generated.client.DatasourceRemoveOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DatasourceRemoveOneProjectionRoot;
import com.hv.catalog.graphql.generated.client.DatasourcesOneGraphQLQuery;
import com.hv.catalog.graphql.generated.client.DatasourcesOneProjectionRoot;
import com.hv.catalog.graphql.generated.types.CreateOneDatasourceInput;
import com.hv.catalog.graphql.generated.types.CreateOneDatasourcePayload;
import com.hv.catalog.graphql.generated.types.Datasource;
import com.hv.catalog.graphql.generated.types.DatasourceAttributesInput;
import com.hv.catalog.graphql.generated.types.FilterFindOneDatasourceInput;
import com.hv.catalog.graphql.generated.types.FilterRemoveOneDatasourceInput;
import com.hv.catalog.graphql.generated.types.RemoveByIdDatasourcePayload;
import com.hv.catalog.graphqlclientjava.util.GraphqlClientUtils;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Disabled("Should run against local app-server")
public class DataSourceTests {

  private static final String DS_NAME = "testDS";
  private static final String DS_FILE_SYSTEM_TYPE = "test type";
  private static final String DS_SOURCE_TYPE = "test source type";
  private static final String DS_SOURCE_URI = "test-uri";

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
  @DisplayName("Query: DatasourceById(_id: MongoID!)")
  @Disabled
  void getDatasourceById() {
    String idString = "61656dc960303eceb53e714c";
    DatasourceByIdGraphQLQuery query = DatasourceByIdGraphQLQuery
        .newRequest()
        ._id(idString)
        .build();
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query,
            new DatasourceByIdProjectionRoot()
                ._id()._logicalId().displayName().dataVersion().type());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    Datasource dataSource = response.extractValueAsObject(query.getOperationName(),
        new TypeRef<Datasource>() {
        });

    Assertions.assertNotNull(dataSource);
    Assertions.assertEquals(idString, dataSource.get_id());
  }


  @Test
  @DisplayName("Query: DatasourcesOne")
  void getDataSourceByName() {
    FilterFindOneDatasourceInput filter = FilterFindOneDatasourceInput
        .newBuilder()
        .name(DS_NAME.toLowerCase())
        .build();
    DatasourcesOneGraphQLQuery query = DatasourcesOneGraphQLQuery
        .newRequest()
        .filter(filter)
        .build();
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query, new DatasourcesOneProjectionRoot()
            ._id().name().type().sourceType().sourceURI());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    Datasource datasource = response.extractValueAsObject(query.getOperationName(), new TypeRef<Datasource>() {
    });
    Assertions.assertNotNull(datasource);
    Assertions.assertEquals(DS_NAME.toLowerCase(), datasource.getName());
//    Assertions.assertEquals(FILE_SYSTEM_TYPE, datasource.getType());
  }

  @Test
  @DisplayName("Mutation: DatasourceCreateOne")
  void createDatasource() {
    List<String> rootPaths = new ArrayList<>();
    rootPaths.add("/testdata");
    DatasourceAttributesInput attributes = DatasourceAttributesInput
        .newBuilder()
        .rootPaths(rootPaths)
        .build();
    CreateOneDatasourceInput input = CreateOneDatasourceInput
        .newBuilder()
        .name(DS_NAME)
        .type(DS_FILE_SYSTEM_TYPE)
        .sourceType(DS_SOURCE_TYPE)
        .sourceURI(DS_SOURCE_URI)
        .attributes(attributes)
        .build();
    DatasourceCreateOneGraphQLQuery query = DatasourceCreateOneGraphQLQuery
        .newRequest()
        .record(input)
        .build();

    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query,
            new DatasourceCreateOneProjectionRoot()
                .recordId()
                .record()._id()._logicalId().name().type().sourceType().sourceURI().dataVersion()
                .attributes().rootPaths());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    CreateOneDatasourcePayload responseObject =
        response.extractValueAsObject(query.getOperationName(), new TypeRef<CreateOneDatasourcePayload>() {
        });
    Assertions.assertNotNull(responseObject);
    Datasource datasource = responseObject.getRecord();
    Assertions.assertNotNull(datasource);

    // @FIXME: names as lower-cased, check with source service
    Assertions.assertEquals(DS_NAME.toLowerCase(), datasource.getName());
    Assertions.assertEquals(DS_FILE_SYSTEM_TYPE, datasource.getType());
    Assertions.assertEquals(DS_SOURCE_TYPE, datasource.getSourceType());
    Assertions.assertEquals(DS_SOURCE_URI, datasource.getSourceURI());
  }

  @Test
  @DisplayName("Mutation: DatasourceRemoveById")
  @Disabled
  void removeDatasourceById() {
    DatasourceRemoveByIdGraphQLQuery query = DatasourceRemoveByIdGraphQLQuery
        .newRequest()
        ._id("6165986c60303eceb53e714e")
        .build();
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query,
            new DatasourceRemoveByIdProjectionRoot()
                .recordId().record()._id().name().type());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    RemoveByIdDatasourcePayload responseObject =
        response.extractValueAsObject(query.getOperationName(), new TypeRef<RemoveByIdDatasourcePayload>() {
        });
    Assertions.assertNotNull(responseObject);
    Datasource datasource = responseObject.getRecord();
    Assertions.assertNotNull(datasource);
  }

  @Test
  @DisplayName("Mutation: DatasourceRemoveOne")
  void removeDsByName() {
    DatasourceRemoveOneGraphQLQuery query = DatasourceRemoveOneGraphQLQuery
        .newRequest()
        .filter(FilterRemoveOneDatasourceInput
            .newBuilder()
            .name("Sdffd")
            .build())
        .build();
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query, new DatasourceRemoveOneProjectionRoot()
            .recordId()
            .record()._id().name().type().sourceType().sourceURI());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    Datasource datasource = response.extractValueAsObject(query.getOperationName(),
        new TypeRef<Datasource>() {
        });
    Assertions.assertNotNull(datasource);
    Assertions.assertEquals(DS_NAME.toLowerCase(), datasource.getName());
  }

  @Test
  @DisplayName("Mutation: DatasourceRemoveOne - Trying to remove a non-existing record")
  void removeDsByNameWhichDoesNotExist() {
    DatasourceRemoveOneGraphQLQuery query = DatasourceRemoveOneGraphQLQuery
        .newRequest()
        .filter(FilterRemoveOneDatasourceInput
            .newBuilder()
            .name("no-such-name-exists")
            .build())
        .build();
    GraphQLQueryRequest request =
        new GraphQLQueryRequest(query,
            new DatasourceRemoveOneProjectionRoot()
                .error()
                .message()
                .getParent()
                .recordId()
                .record()._id().name());
    Mono<GraphQLResponse> responseMono = client.reactiveExecuteQuery(request.serialize());
    GraphQLResponse response = responseMono.block();
    Assertions.assertNotNull(response);
    Datasource datasource = response.extractValueAsObject(query.getOperationName(),
        new TypeRef<Datasource>() {
        });
    Assertions.assertNull(datasource);
  }
}
