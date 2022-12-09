package ziph;

import java.util.ArrayList;
import java.util.List;

class KeyValue {
  public String key;
  public String type;
  public String value;

  public KeyValue(String key, String type, String value) {
    this.key = key;
    this.type = type;
    this.value = value;
  }
}

public class JSONObject {
  private List<KeyValue> jsonData;

  public JSONObject() {
    this.jsonData = new ArrayList<KeyValue>();
  }

  private int checkIfSetKeyInsteadOfAddKey(String key) {
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

  public void setNull(String key) {
    int setIndex = this.checkIfSetKeyInsteadOfAddKey(key);
    if (setIndex != -1) {
      this.jsonData.set(setIndex, new KeyValue(key, "null", "null"));
      return;
    }
    this.jsonData.add(new KeyValue(key, "null", "null"));
  }

  public Object get(String key) {
    for (int i = 0; i < this.jsonData.size(); i++) {
      KeyValue currentKeyValue = this.jsonData.get(i);
      if (currentKeyValue.key.equals(key))
        switch (currentKeyValue.type) {
          case "bool":
            return (currentKeyValue.value.equals("true") ? true : false);
          case "int":
            return Integer.parseInt(currentKeyValue.value);
          case "float":
            return Float.parseFloat(currentKeyValue.value);
          case "double":
            return Double.parseDouble(currentKeyValue.value);
          case "string":
            return currentKeyValue.value;
          case "null":
            return null;
        }
    }
    return null;
  }

  public List<String> getKeys() {
    List<String> keys = new ArrayList<String>();
    for (int i = 0; i < this.jsonData.size(); i++)
      keys.add(this.jsonData.get(i).key);
    return keys;
  }

  public void delete(String key) {
    for (int i = 0; i < this.jsonData.size(); i++)
      if (this.jsonData.get(i).key.equals(key)) {
        this.jsonData.remove(i);
        return;
      }
  }

  public String toString() {
    String jsonString = "{";
    for (int i = 0; i < this.jsonData.size(); i++) {
      KeyValue currentKeyValue = this.jsonData.get(i);
      String valueAsJsonValue = "";
      if (currentKeyValue.type.equals("string")) valueAsJsonValue = "\"" + currentKeyValue.value + "\"";
      else valueAsJsonValue = currentKeyValue.value;
      jsonString += "\"" + currentKeyValue.key.replaceAll("\"", "\\\"") + "\":" + valueAsJsonValue.replaceAll("\"", "\\\"") + (i != jsonData.size() - 1 ? "," : "");
    }
    return jsonString + "}";
  }
}
