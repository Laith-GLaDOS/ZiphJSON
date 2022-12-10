package ziph;

import java.util.ArrayList;

public class JSONObjectFromString extends JSONObject {
  private void parseString(String jsonString) throws InvalidJSONException {
    if (jsonString.replaceAll("\n", "").replaceAll(" ", "").equals("{}"))
      return;

    String trimmedJsonString = jsonString.trim();

    if (!trimmedJsonString.startsWith("{") || !trimmedJsonString.endsWith("}"))
      throw new InvalidJSONException();

    String[] jsonKeysAndValues = trimmedJsonString.replaceAll("^\\{", "").replaceAll("}$", "").split(",");
    for (int i = 0; i < jsonKeysAndValues.length; i++) {
      jsonKeysAndValues[i] = jsonKeysAndValues[i].trim();
      KeyValue currentKeyValue = null;
      String[] currentKeyValueAsStringArray = jsonKeysAndValues[i].split(":");
      if (currentKeyValueAsStringArray.length != 2)
        throw new InvalidJSONException();
      String currentValue = currentKeyValueAsStringArray[1].trim();
      String currentKey = jsonKeysAndValues[i].split(":")[0].trim().replaceAll("^\\\"|\"$", "");
      try {
        if (!currentValue.equals("true") && !currentValue.equals("false")) throw new Exception();
        currentKeyValue = new KeyValue(currentKey, "bool", currentValue);
      } catch (Exception e0) {
        try {
          Integer.parseInt(currentValue);
          currentKeyValue = new KeyValue(currentKey, "int", currentValue);
        } catch (Exception e1) {
          try {
            Float.parseFloat(currentValue);
            currentKeyValue = new KeyValue(currentKey, "float", currentValue);
          } catch (Exception e2) {
            try {
              Double.parseDouble(currentValue);
              currentKeyValue = new KeyValue(currentKey, "double", currentValue);
            } catch (Exception e3) {
              if (currentValue.startsWith("\"") && currentValue.endsWith("\""))
                currentKeyValue = new KeyValue(currentKey, "string", currentValue.replace("\"", "").replaceAll("\"$", ""));
              else if (currentValue.startsWith("{") && currentValue.endsWith("}"))
                currentKeyValue = new KeyValue(currentKey, "object", currentValue);
            }
          }
        }
      }
      if (currentKeyValue == null) throw new InvalidJSONException();
      int setIndex = super.checkIfSetKeyInsteadOfAddKey(currentKeyValue.key);
      if (setIndex != -1) super.jsonData.set(setIndex, currentKeyValue);
      else super.jsonData.add(currentKeyValue);
    }
  }

  public JSONObjectFromString(String jsonString) throws InvalidJSONException {
    super();
    super.jsonData = new ArrayList<KeyValue>();
    this.parseString(jsonString);
  }
}
