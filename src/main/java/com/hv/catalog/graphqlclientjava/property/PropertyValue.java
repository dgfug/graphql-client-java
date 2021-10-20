package com.hv.catalog.graphqlclientjava.property;

import java.io.IOException;
import java.io.Serializable;

public interface PropertyValue extends Serializable {

  boolean isInt();

  boolean isLong();

  boolean isValue();

  boolean isArray();

  boolean isDouble();

  boolean isString();

  boolean isBoolean();

  boolean isProperties();

  boolean isByteArray();

  int getIntValue();

  long getLongValue();

  double getDoubleValue();

  String getStringValue();

  boolean getBooleanValue();

  PropertyArray getArrayValue();

  Properties getPropertiesValue();

  byte[] getByteArrayValue() throws IOException;
}
