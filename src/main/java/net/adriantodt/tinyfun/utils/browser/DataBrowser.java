package net.adriantodt.tinyfun.utils.browser;

import java.util.List;
import java.util.Map;

public abstract class DataBrowser {
    /**
     * @return True if the value represents a list.
     */
    public abstract boolean isList();

    /**
     * @return True if the value represents a map.
     */
    public abstract boolean isMap();

    /**
     * @return True if the node is null
     */
    public abstract boolean isNull();

    /**
     * Get an element at an index for a list value
     * @param index List index
     * @return JsonBrowser instance which wraps the value at the specified index
     */
    public abstract DataBrowser index(int index);

    /**
     * Get an element by key from a map value
     * @param key Map key
     * @return JsonBrowser instance which wraps the value with the specified key
     */
    public abstract DataBrowser get(String key);

    /**
     * Put a value into the map if this instance contains a map.
     * @param key The map entry key
     * @param item The map entry value
     */
    public abstract void put(String key, Object item);

    /**
     * Returns a list of all the values in this element
     * @return The list of values as JsonBrowser elements
     */
    public abstract List<DataBrowser> values();

    /**
     * @return The value of the element as text
     */
    public abstract String text();

    public abstract boolean asBoolean(boolean defaultValue);

    public abstract long asLong(long defaultValue);

    public abstract String safeText();

    public abstract Object raw();

    @SuppressWarnings("unchecked")
    public static DataBrowser create(Object obj) {
        if (obj == null) {
            return NullBrowser.INSTANCE;
        }
        if (obj instanceof DataBrowser) {
            return (DataBrowser) obj;
        }
        if (obj instanceof Map) {
            return new MapBrowser((Map<Object, Object>) obj);
        }
        if (obj instanceof List) {
            return new ListBrowser((List<Object>) obj);
        }
        return new ValueBrowser(obj);
    }
}
