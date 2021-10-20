package com.hv.catalog.graphqlclientjava.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Iterator;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertiesImpl
    extends PropertyValueImpl implements Properties {

  protected PropertiesImpl() {

    super(PropertiesUtil.
        mapper.createObjectNode());
  }

  public PropertiesImpl
      (JsonNode json) {
    super(json);
  }

  @Override
  public String json() {

    try {
      return PropertiesUtil.mapper.writeValueAsString(json);
    } catch (JsonProcessingException e) {
      return json.toString();
    }
  }

  @Override
  public Iterator<String> getNames() {

    return json.fieldNames();
  }

  @Override
  public boolean isInt(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isInt();
  }

  @Override
  public boolean isLong(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isLong() || value.isInt();
  }

  @Override
  public boolean isValue(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isValue();
  }

  @Override
  public boolean isArray(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isArray();
  }

  @Override
  public boolean isDouble(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isDouble();

  }

  @Override
  public boolean isString(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isString();

  }

  @Override
  public boolean isBoolean(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isBoolean();
  }

  @Override
  public boolean isProperties(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isProperties();

  }

  @Override
  public boolean isByteArray(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return false;
    }
    return value.isByteArray();

  }

  @Override
  public int getIntValue(String name) {

    PropertyValue value = getValue(name);
    if (value != null) {
      return value.getIntValue();
    }
    return -1;

  }

  @Override
  public long getLongValue(String name) {

    PropertyValue value = getValue(name);

    if (value != null) {
      return value.getLongValue();
    }
    return -1;

  }

  @Override
  public double getDoubleValue(String name) {

    PropertyValue value = getValue(name);

    if (value != null) {
      return value.getDoubleValue();
    } else {
      return -1;
    }
  }

  @Override
  public String getStringValue(String name) {

    PropertyValue value = getValue(name);
    if (value == null) {
      return null;
    }
    return value.getStringValue();

  }

  @Override
  public boolean getBooleanValue(String name) {

    PropertyValue value = getValue(name);
    if (value != null) {
      return value.getBooleanValue();
    }
    return false;

  }

  @Override
  public PropertyArray getArray(String name) {

    JsonNode node = json.get(name);
    if (node == null) {
      return null;
    }
    return new PropertyArrayImpl(node);

  }

  @Override
  public PropertyArray createArray(String name) {

    ArrayNode array = PropertiesUtil.mapper.createArrayNode();
    ((ObjectNode) json).put(name, array);
    return new PropertyArrayImpl(array);
  }

  @Override
  public Properties getProperties(String name) {

    JsonNode node = json.get(name);
    if (node == null) {
      return null;
    }
    return new PropertiesImpl(node);

  }

  @Override
  public byte[] getByteArray(String name) throws IOException {

    PropertyValue value = getValue(name);
    if (value == null) {
      return null;
    }
    return value.getByteArrayValue();

  }

  @Override
  public Properties createProperties(String name) {

    ObjectNode object = PropertiesUtil.mapper.createObjectNode();
    ((ObjectNode) json).put(name, object);
    return new PropertiesImpl(object);
  }

  @Override
  public void removeAll() {

    ((ObjectNode) json).removeAll();
  }

  @Override
  public void remove(String name) {

    if (name != null) {
      ((ObjectNode) json).remove(name);
    }
  }

  @Override
  public void setInt(String name, int value) {

    if (name != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public void setLong(String name, long value) {

    if (name != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public void setDouble(String name, double value) {

    if (name != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public void setString(String name, String value) {
    if (name != null && value != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public void setBoolean(String name, boolean value) {

    if (name != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public void setArray(String name, PropertyArray value) {

    if (name != null && value != null) {
      ((ObjectNode) json).put(name, ((PropertyArrayImpl) value).json);
    }
  }

  @Override
  public void setProperties(String name, Properties value) {

    if (name != null && value != null) {
      ((ObjectNode) json).put(name, ((PropertiesImpl) value).json);
    }
  }

  @Override
  public void setByteArray(String name, byte[] value) {

    if (name != null && value != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  private PropertyValue getValue(String name) {

    JsonNode node = json.get(name);
    if (node == null) {
      return null;
    }
    return new PropertyValueImpl(node);
  }

  public JsonNode getJsonNode(String name) {

    return json.get(name);
  }

  protected void setJsonNode(String name, JsonNode value) {

    if (name != null && value != null) {
      ((ObjectNode) json).put(name, value);
    }
  }

  @Override
  public String toString() {

    return this.json();
  }
}
