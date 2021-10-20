package com.hv.catalog.graphqlclientjava.property;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class PropertiesUtil {

  public
  static ObjectMapper mapper = new ObjectMapper();

  public
  static Properties createProperties() {
    return new PropertiesImpl();
  }

  public
  static Properties createProperties(String json) throws IOException {
    return new PropertiesImpl(mapper.readTree(json));
  }

  public
  static Properties loadProperties(File file) throws IOException {
    return new PropertiesImpl(mapper.readTree(file));
  }

  public static void saveProperties(File file, Properties properties)
      throws IOException {

    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(file.getPath()))) {
      writer.write(properties.toString());
    }
  }

  public
  static Properties createProperties(JsonNode json) {
    return new PropertiesImpl(json);
  }

  public static Properties createProperties(Map<String, String> propMap) {
    Properties properties = createProperties();
    propMap.forEach((key, value) -> {
      properties.setString(key, value);
    });
    return properties;
  }

  public
  static JsonNode getPropertiesJson(Properties properties) {
    return ((PropertiesImpl) properties).json;
  }

  public static void main(String[] args) throws Exception {

    Properties properties;
    properties = loadProperties(new File("/Users/jason/Downloads/58.json.json"));

    String json1;
    String json2;
    String json3;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ByteArrayInputStream bis;
    ByteArrayOutputStream bos;

    json1 = properties.json();
    System.out.println("before: " + json1.length());
    System.out.println(json1);
    bos = new ByteArrayOutputStream();
    oos = new ObjectOutputStream(bos);
    oos.writeObject(json1);
    oos.flush();
    byte[] ba0 = bos.toByteArray();
    bis = new ByteArrayInputStream(ba0);
    ois = new ObjectInputStream(bis);
    json2 = (String) ois.readObject();
    System.out.println("after: " + json2.length());
    System.out.println(json2);
    properties = createProperties(mapper.readTree(json2));
    ois.close();
    oos.close();
    json3 = properties.json();
    System.out.println("after: " + json3.length());
    System.out.println(json3);
  }
}
