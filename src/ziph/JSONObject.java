package ziph;

import java.util.ArrayList;
import java.util.List;

public class JSONObject {
  public List<KeyValue> jsonData;

  public JSONObject() {
    this.jsonData = new ArrayList<>();
  }

  public int checkIfSetKeyInsteadOfAddKey(String key) {
    int setIndex = -1;

    for (int i = 0; i < this.jsonData.size(); i++)
      if (this.jsonData.get(i).key.equals(key)) {
        setIndex = i;
        break;
      }

    return setIndex;
  }
  public void set(String key, boolean value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "bool", value ? "true" : "false"));
      return;
    }
    this.jsonData.add(new KeyValue(key, "bool", value ? "true" : "false"));
  }
  public void set(String key, int value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "int", Integer.toString(value)));
      return;
    }
    this.jsonData.add(new KeyValue(key, "int", Integer.toString(value)));
  }
  public void set(String key, float value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "float", Float.toString(value)));
      return;
    }
    this.jsonData.add(new KeyValue(key, "float", Float.toString(value)));
  }
  public void set(String key, double value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "double", Double.toString(value)));
      return;
    }
    this.jsonData.add(new KeyValue(key, "double", Double.toString(value)));
  }
  public void set(String key, String value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "string", value));
      return;
    }
    this.jsonData.add(new KeyValue(key, "string", value));
  }
  public void set(String key, JSONObject value) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "object", value.toJSONString()));
      return;
    }
    this.jsonData.add(new KeyValue(key, "object", value.toJSONString()));
  }

  public void setNull(String key) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "null", "null"));
      return;
    }
    this.jsonData.add(new KeyValue(key, "null", "null"));
  }

  public Object get(String key) {
    for (KeyValue currentKeyValue : this.jsonData) {
      if (currentKeyValue.key.equals(key))
        switch (currentKeyValue.type) {
          case "bool":
            return currentKeyValue.value.equals("true");
          case "int":
            return Integer.parseInt(currentKeyValue.value);
          case "float":
            return Float.parseFloat(currentKeyValue.value);
          case "double":
            return Double.parseDouble(currentKeyValue.value);
          case "string":
            return currentKeyValue.value;
          case "object":
            try {
              return new JSONObjectFromString(currentKeyValue.value);
            } catch (InvalidJSONException ignored) {
            }
          case "null":
            return null;
        }
    }
    return null;
  }

  public List<String> getKeys() {
    List<String> keys = new ArrayList<>();
    for (KeyValue currentKeyValue : this.jsonData) keys.add(currentKeyValue.key);
    return keys;
  }

  public void delete(String key) {
    for (int i = 0; i < this.jsonData.size(); i++)
      if (this.jsonData.get(i).key.equals(key)) {
        this.jsonData.remove(i);
        return;
      }
  }

  public String toJSONString() {
    StringBuilder jsonString = new StringBuilder("{");
    for (int i = 0; i < this.jsonData.size(); i++) {
      KeyValue currentKeyValue = this.jsonData.get(i);
      String valueAsJsonValue;
      if (currentKeyValue.type.equals("string")) valueAsJsonValue = "\"" + currentKeyValue.value + "\"";
      else valueAsJsonValue = currentKeyValue.value;
      jsonString.append("\"").append(currentKeyValue.key.replaceAll("\"", "\\\"")).append("\":").append(valueAsJsonValue.replaceAll("\"", "\\\"")).append(i != jsonData.size() - 1 ? "," : "");
    }
    return jsonString + "}";
  }
}
