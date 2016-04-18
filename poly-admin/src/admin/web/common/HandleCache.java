package admin.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HandleCache
{
  private static Map<String, Object> mapObject = new HashMap<String, Object>();
  
  public static void put(String key, Object value)
  {
    mapObject.put(key, value);
  }
  
  public static void add(String key, Object value)
  {
    if (!mapObject.containsKey(key)) {
      mapObject.put(key, value);
    }
  }
  
  public static Object get(String key)
  {
    return mapObject.get(key);
  }
  
  public static void clear()
  {
    mapObject = new HashMap<String, Object>();
  }
  
  public static Object remove(String key)
  {
    return mapObject.remove(key);
  }
  
  public static void removeContain(String key)
  {
    Set<String> setKey = mapObject.keySet();
    List<String> lstRemove = new ArrayList<String>();
    String str;
    for (Iterator<String> iterator = setKey.iterator(); iterator.hasNext();)
    {
      str = (String)iterator.next();
      if (str.startsWith(key)) {
        lstRemove.add(str);
      }
    }
    for (String remove : lstRemove) {
      mapObject.remove(remove);
    }
  }
}
