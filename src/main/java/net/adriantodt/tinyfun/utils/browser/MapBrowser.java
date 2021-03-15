package net.adriantodt.tinyfun.utils.browser;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapBrowser extends DataBrowser {
  private final @NotNull Map<Object, Object> map;

  public MapBrowser(@NotNull Map<Object, Object> map) {
    this.map = map;
  }

  @Override
  public boolean isList() {
    return false;
  }

  @Override
  public boolean isMap() {
    return true;
  }

  public boolean isNull() {
    return false;
  }

  @Override
  public DataBrowser index(int index) {
    return NullBrowser.INSTANCE;
  }

  @Override
  public DataBrowser get(String key) {
    if (isMap()) {
      return create(map.get(key));
    } else {
      return NullBrowser.INSTANCE;
    }
  }

  @Override
  public void put(String key, Object item) {
    if (item instanceof DataBrowser) {
      map.put(key, ((DataBrowser) item).raw());
    } else {
      map.put(key, item);
    }
  }

  @Override
  public List<DataBrowser> values() {
    return map.values().stream().map(DataBrowser::create).collect(Collectors.toList());
  }

  @Override
  public String text() {
    return map.toString();
  }

  @Override
  public boolean asBoolean(boolean defaultValue) {
    return defaultValue;
  }

  @Override
  public long asLong(long defaultValue) {
    return defaultValue;
  }

  @Override
  public String safeText() {
    String text = text();
    return text != null ? text : "";
  }

  @Override
  public Map<Object, Object> raw() {
    return map;
  }
}
