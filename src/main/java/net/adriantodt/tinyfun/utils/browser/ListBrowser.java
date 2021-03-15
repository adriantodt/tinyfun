package net.adriantodt.tinyfun.utils.browser;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ListBrowser extends DataBrowser {
    private final @NotNull List<Object> list;

    public ListBrowser(@NotNull List<Object> node) {
        this.list = node;
    }

    public boolean isList() {
        return true;
    }

    public boolean isMap() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public DataBrowser index(int index) {
        if (index >= 0 && index < list.size()) {
            return create(list.get(index));
        } else {
            return NullBrowser.INSTANCE;
        }
    }

    public DataBrowser get(String key) {
        return NullBrowser.INSTANCE;
    }

    public void put(String key, Object item) {
        throw new IllegalStateException("Put only works on a map");
    }

    public List<DataBrowser> values() {
        return list.stream().map(DataBrowser::create).collect(Collectors.toList());
    }

    public String text() {
        return list.toString();
    }

    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    public long asLong(long defaultValue) {
        return defaultValue;
    }

    public String safeText() {
        String text = text();
        return text != null ? text : "";
    }

    @Override
    public List<Object> raw() {
        return list;
    }
}
