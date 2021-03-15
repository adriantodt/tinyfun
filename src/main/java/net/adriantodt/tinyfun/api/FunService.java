package net.adriantodt.tinyfun.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

/**
 * A service that creates TinyFun functions. Returns null if it can't load the function.
 */
public interface FunService {
    @NotNull String name();

    @Nullable Fun load(File dir, Map<String, Object> options);
}
