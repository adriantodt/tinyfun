package net.adriantodt.tinyfun.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class AbstractFun<T extends FunService> implements Fun {
    private final @NotNull T parent;
    private final @NotNull String name;

    public AbstractFun(@NotNull T parent, @NotNull String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull T service() {
        return parent;
    }

    @Override
    public void close() throws IOException {
    }
}
