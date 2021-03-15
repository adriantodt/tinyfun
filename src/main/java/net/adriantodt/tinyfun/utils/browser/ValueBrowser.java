package net.adriantodt.tinyfun.utils.browser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ValueBrowser extends DataBrowser {
    private final @NotNull Object node;

    public ValueBrowser(@NotNull Object node) {
        this.node = node;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public DataBrowser index(int index) {
        return NullBrowser.INSTANCE;
    }

    @Override
    public DataBrowser get(String key) {
        return NullBrowser.INSTANCE;
    }

    @Override
    public void put(String key, Object item) {
        throw new IllegalStateException("Put only works on a map");
    }

    @Override
    public List<DataBrowser> values() {
        return new ArrayList<>();
    }

    @Override
    public String text() {
        return node.toString();
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        if (node instanceof Boolean) {
            return (boolean) node;
        } else if (node instanceof String) {
            if ("true".equals(node)) {
                return true;
            } else if ("false".equals(node)) {
                return false;
            }
        }

        return defaultValue;
    }

    @Override
    public long asLong(long defaultValue) {
        if (node instanceof Number) {
            return ((Number) node).longValue();
        } else if (node instanceof String) {
            try {
                return Long.parseLong((String) node);
            } catch (NumberFormatException ignored) {
                // Fall through to default value.
            }
        }

        return defaultValue;
    }

    @Override
    public String safeText() {
        String text = text();
        return text != null ? text : "";
    }

    @Override
    public Object raw() {
        return node;
    }
}
