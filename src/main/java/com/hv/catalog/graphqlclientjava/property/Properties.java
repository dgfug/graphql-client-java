package com.hv.catalog.graphqlclientjava.property;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Type defining the properties interface for an entity.
 */
public
interface Properties
    extends Serializable {

  enum PropertyValueType {
    INT,
    LONG,
    ARRAY,
    DOUBLE,
    STRING,
    BOOLEAN,
    PROPERTIES
  }

  ;

  String json();

  /**
   * An iterator of names of properties.
   */
  Iterator<String> getNames();

  boolean isInt(String name);

  boolean isLong(String name);

  boolean isValue(String name);

  boolean isArray(String name);

  boolean isDouble(String name);

  boolean isString(String name);

  boolean isBoolean(String name);

  boolean isProperties(String name);

  default boolean isByteArray(String name) {
    throw new RuntimeException("Not Yet Implemented");
  }

  int getIntValue(String name);

  long getLongValue(String name);

  double getDoubleValue(String name);

  String getStringValue(String name);

  boolean getBooleanValue(String name);

  PropertyArray getArray(String name);

  PropertyArray createArray(String name);

  Properties getProperties(String name);

  default byte[] getByteArray(String name) throws IOException {
    throw new RuntimeException("Not Yet Implemented");
  }

  Properties createProperties(String name);

  void removeAll();

  void remove(String name);

  void setInt(String name, int value);

  void setLong(String name, long value);

  void setDouble(String name, double value);

  void setString(String name, String value);

  void setBoolean(String name, boolean value);

  void setArray(String name, PropertyArray value);

  void setProperties(String name, Properties value);

  default void setByteArray(String name, byte[] value) {
    throw new RuntimeException("Not Yet Implemented");
  }
}
