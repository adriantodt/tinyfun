package net.adriantodt.tinyfun.impl;

import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonWriter;
import net.adriantodt.tinyfun.api.AbstractFun;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.*;
import java.util.Map;

public class ScriptingFun extends AbstractFun<ScriptingFunService> {
    private final File dir;
    private final File scriptFile;
    private final ScriptEngine engine;
    private final Map<String, Object> options;

    public ScriptingFun(
        ScriptingFunService parent,
        File dir, File scriptFile,
        ScriptEngine engine,
        Map<String, Object> options
    ) {
        super(parent, dir.getName());
        this.dir = dir;
        this.scriptFile = scriptFile;
        this.engine = engine;
        this.options = options;
    }

    @Override
    public boolean execute(InputStream input, OutputStream output) throws Exception {
        try (var in = input; var script = new FileReader(scriptFile); var out = output) {
            var event = JsonParser.any().from(in);

            var bindings = engine.createBindings();
            bindings.put("event", event);
            var result = engine.eval(script, bindings);

            if (result instanceof Invocable) {
                System.out.println("INVOCABLE:" + result);
            }

            JsonWriter.on(out).value(result).done();
        }
        return true;
    }
}
