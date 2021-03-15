package net.adriantodt.tinyfun.api;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A TinyFun function.
 */
public interface Fun extends Closeable {
    @NotNull String name();

    @NotNull FunService service();

    boolean execute(InputStream input, OutputStream output) throws Exception;
}
