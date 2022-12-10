package ziph;

import java.util.ArrayList;

public class JSONObjectFromString extends JSONObject {
  private void parseString(String jsonString) throws InvalidJSONException {
    if (jsonString.replaceAll("\n", "").replaceAll(" ", "").equals("{}"))
      return;

    String trimmedJsonString = jsonString.trim();

    if (!trimmedJsonString.startsWith("{") || !trimmedJsonString.endsWith("}"))
      throw new InvalidJSONException();

    String[] jsonKeysAndValues = trimmedJsonString.replace("{", "").replaceAll("}$", "").split(",");
    for (int i = 0; i < jsonKeysAndValues.length; i++) {
      jsonKeysAndValues[i] = jsonKeysAndValues[i].trim();
      KeyValue currentKeyValue = null;
      String[] currentKeyValueAsStringArray = jsonKeysAndValues[i].split(":");
      if (currentKeyValueAsStringArray.length != 2)
        throw new InvalidJSONException();
      String currentValue = currentKeyValueAsStringArray[1].trim();
      try {
        if (currentValue != "true" && currentValue != "false") throw new Exception();
        currentKeyValue = new KeyValue(jsonKeysAndValues[i].split(":")[0].trim().replace("\"", "").replaceAll("\"$", ""), "bool", currentValue);
      } catch (Exception e0) {
        try {
          Integer.parseInt(currentValue);
          currentKeyValue = new KeyValue(jsonKeysAndValues[i].split(":")[0].trim().replace("\"", "").replaceAll("\"$", ""), "int", currentValue);
        } catch (Exception e1) {
          try {
            Float.parseFloat(currentValue);
            currentKeyValue = new KeyValue(jsonKeysAndValues[i].split(":")[0].trim().replace("\"", "").replaceAll("\"$", ""), "float", currentValue);
          } catch (Exception e2) {
            try {
              Double.parseDouble(currentValue);
              currentKeyValue = new KeyValue(jsonKeysAndValues[i].split(":")[0].trim().replace("\"", "").replaceAll("\"$", ""), "double", currentValue);
            } catch (Exception e3) {
              if (currentValue.startsWith("\"") && currentValue.endsWith("\""))
                currentKeyValue = new KeyValue(jsonKeysAndValues[i].split(":")[0].trim().replace("\"", "").replaceAll("\"$", ""), "string", currentValue.replace("\"", "").replaceAll("\"$", ""));
            }
          }
        }
      }
      if (currentKeyValue == null) throw new InvalidJSONException();
      int setIndex = checkIfSetKeyInsteadOfAddKey(currentKeyValue.key);
      if (setIndex != -1) this.jsonData.set(setIndex, currentKeyValue);
      else this.jsonData.add(currentKeyValue);
    }
  }

  public JSONObjectFromString(String jsonString) throws InvalidJSONException {
    this.jsonData = new ArrayList<KeyValue>();
    this.parseString(jsonString);
  }
}
