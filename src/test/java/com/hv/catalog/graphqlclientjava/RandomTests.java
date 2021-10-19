package com.hv.catalog.graphqlclientjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hv.catalog.graphqlclientjava.property.Properties;
import com.hv.catalog.graphqlclientjava.property.PropertiesImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class RandomTests {

  @Test
  void extractExecutionRecordFromVirtualFolderAttributes() throws JsonProcessingException {
    Map<Object, Object> map = new HashMap<>();
    String executionRecordValue = "{\n" +
        "  \"/hello.txt\": {\n" +
        "    \"HdfsFormatDiscoverer\": {\n" +
        "      \"path\": \"/hello.txt\",\n" +
        "      \"klass\": \"HdfsFormatDiscoverer\",\n" +
        "      \"start\": 1634578088500,\n" +
        "      \"end\": 1634578105908\n" +
        "    }\n" +
        "  }\n" +
        "}";
    map.put("executionRecordValue", executionRecordValue);
    String record = map.get("executionRecordValue").toString();
    System.out.println("executionRecordValue: toString: from map\n" + record);

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(executionRecordValue);
    System.out.println("jsonNode: " + jsonNode);
    String path = "/hello.txt";
    JsonNode pathNode = jsonNode.get(path);
    System.out.println("pathNode: " + pathNode);

    Properties properties = new PropertiesImpl(null);
    System.out.println("Properties: " + properties);
//    Properties properties1 = properties.getProperties(path);
//    System.out.println("Path Properties: " + properties1);
  }

}