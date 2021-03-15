package net.adriantodt.tinyfun.utils.browser;

import java.util.ArrayList;
import java.util.List;

public class NullBrowser extends DataBrowser {
    public static final NullBrowser INSTANCE = new NullBrowser();

    private NullBrowser() {
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
        return true;
    }

    @Override
    public NullBrowser index(int index) {
        return INSTANCE;
    }

    @Override
    public NullBrowser get(String key) {
        return INSTANCE;
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
        return null;
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
        return "";
    }

    @Override
    public Object raw() {
        return null;
    }
}
