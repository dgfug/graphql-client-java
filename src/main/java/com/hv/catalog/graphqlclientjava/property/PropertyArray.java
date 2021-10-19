package com.hv.catalog.graphqlclientjava.property;

public interface PropertyArray
    extends PropertyValue {

  int size();

  boolean isInt(int index);

  boolean isLong(int index);

  boolean isArray(int index);

  boolean isDouble(int index);

  boolean isString(int index);

  boolean isBoolean(int index);

  boolean isProperties(int index);

  int getInt(int index);

  long getLong(int index);

  double getDouble(int index);

  String getString(int index);

  boolean getBoolean(int index);

  PropertyArray getArray(int index);

  Properties getProperties(int index);

  void setProperties(int index, Properties value);

  void addInt(int value);

  void addLong(long value);

  void addDouble(double value);

  void addString(String value);

  void addBoolean(boolean value);

  void addArray(PropertyArray value);

  void addProperties(Properties value);
}
