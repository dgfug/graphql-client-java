package com.hv.catalog.graphqlclientjava.property;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public
class PropertyValueImpl
    implements Serializable, PropertyValue {

  public JsonNode json;

  PropertyValueImpl
      (JsonNode json) {
    this.json = json;
  }

  @Override
  public boolean isInt() {
    return json.isInt();
  }

  @Override
  public boolean isLong() {
    return json.isLong();
  }

  @Override
  public boolean isValue() {
    return json.isValueNode();
  }

  @Override
  public boolean isArray() {
    return json.isArray();
  }

  @Override
  public boolean isDouble() {
    return json.isDouble();
  }

  @Override
  public boolean isString() {
    return json.isTextual();
  }

  @Override
  public boolean isBoolean() {
    return json.isBoolean();
  }

  @Override
  public boolean isProperties() {
    return json.isObject();
  }

  @Override
  public boolean isByteArray() {
    return json.isBinary();
  }

  @Override
  public int getIntValue() {
    return json.intValue();
  }

  @Override
  public long getLongValue() {
    return json.longValue();
  }

  @Override
  public double getDoubleValue() {
    return json.doubleValue();
  }

  @Override
  public String getStringValue() {
    return json.textValue();
  }

  @Override
  public byte[] getByteArrayValue() throws IOException {
    return json.binaryValue();
  }

  @Override
  public boolean getBooleanValue() {
    return json.booleanValue();
  }

  @Override
  public PropertyArray getArrayValue() {
    return new PropertyArrayImpl(json);
  }

  @Override
  public Properties getPropertiesValue() {
    return new PropertiesImpl(json);
  }

  private void writeObject(ObjectOutputStream out)
      throws IOException {
    out.writeObject(PropertiesUtil.mapper.writeValueAsString(json));
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    json = PropertiesUtil.mapper.readTree((String) in.readObject());
  }

  public JsonNode toJsonNode() {
    return json;
  }

  public JsonNode getJsonNode() {
    return this.toJsonNode();
  }
}
