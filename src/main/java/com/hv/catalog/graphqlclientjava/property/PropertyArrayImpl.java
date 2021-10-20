package com.hv.catalog.graphqlclientjava.property;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@SuppressWarnings("serial")
public class PropertyArrayImpl
    extends PropertyValueImpl implements PropertyArray {

  PropertyArrayImpl() {
    super(PropertiesUtil.mapper.createArrayNode());
  }

  public PropertyArrayImpl(JsonNode json) {
    super(json);
  }

  /*
  @Override
  public List<PropertyValue> getElements() {
      List<PropertyValue> elements = new ArrayList<PropertyValue>();
      while (json.elements().hasNext())
          elements.add(new PropertyValueImpl(json.elements().next()));
      return elements;
  }
  */
  @Override
  public int size() {
    return ((ArrayNode) json).size();
  }

  @Override
  public boolean isInt(int index) {
    return ((ArrayNode) json).get(index).isInt();
  }

  @Override
  public boolean isLong(int index) {
    return ((ArrayNode) json).get(index).isLong();
  }

  @Override
  public boolean isDouble(int index) {
    return ((ArrayNode) json).get(index).isDouble();
  }

  @Override
  public boolean isString(int index) {
    return ((ArrayNode) json).get(index).isTextual();
  }

  @Override
  public boolean isBoolean(int index) {
    return ((ArrayNode) json).get(index).isBoolean();
  }

  @Override
  public boolean isArray(int index) {
    return ((ArrayNode) json).get(index).isArray();
  }

  @Override
  public boolean isProperties(int index) {
    return ((ArrayNode) json).get(index).isObject();
  }

  @Override
  public void addInt(int value) {
    ((ArrayNode) json).add(value);
  }

  @Override
  public void addLong(long value) {
    ((ArrayNode) json).add(value);
  }

  @Override
  public void addDouble(double value) {
    ((ArrayNode) json).add(value);
  }

  @Override
  public void addString(String value) {
    ((ArrayNode) json).add(value);
  }

  @Override
  public void addBoolean(boolean value) {
    ((ArrayNode) json).add(value);
  }

  @Override
  public void addArray(PropertyArray value) {
    ((ArrayNode) json).
        add(((PropertyArrayImpl) value).json);
  }

  @Override
  public void addProperties(Properties value) {
    ((ArrayNode) json).add(((PropertiesImpl) value).json);
  }

  @Override
  public int getInt(int index) {
    return ((ArrayNode) json).get(index).intValue();
  }

  @Override
  public long getLong(int index) {
    return ((ArrayNode) json).get(index).longValue();
  }

  @Override
  public double getDouble(int index) {
    return ((ArrayNode) json).get(index).doubleValue();
  }

  @Override
  public String getString(int index) {
    return ((ArrayNode) json).get(index).textValue();
  }

  @Override
  public boolean getBoolean(int index) {
    return ((ArrayNode) json).get(index).booleanValue();
  }

  @Override
  public Properties getProperties(int index) {
    return new PropertiesImpl(((ArrayNode) json).get(index));
  }

  @Override
  public void setProperties(int index, Properties value) {
    ((ArrayNode) json).set(index, ((PropertiesImpl) value).json);
  }

  @Override
  public PropertyArray getArray(int index) {
    return new PropertyArrayImpl(((ArrayNode) json).get(index));
  }
}
