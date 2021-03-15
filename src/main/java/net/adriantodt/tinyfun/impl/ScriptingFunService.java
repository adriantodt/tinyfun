package net.adriantodt.tinyfun.impl;

import net.adriantodt.tinyfun.api.Fun;
import net.adriantodt.tinyfun.api.FunService;
import net.adriantodt.tinyfun.utils.Util;
import net.adriantodt.tinyfun.utils.browser.DataBrowser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngineManager;
import java.io.File;
import java.util.Map;

public class ScriptingFunService implements FunService {
    private final ScriptEngineManager manager = new ScriptEngineManager();

    @Override
    public @NotNull String name() {
        return "script";
    }

    @Override
    public @Nullable Fun load(File dir, Map<String, Object> options) {
        DataBrowser opt = DataBrowser.create(options);

        var fileName = opt.get("file").text();

        if (fileName != null) {
            var scriptFile = new File(dir, fileName);
            for (String ext : Util.possibleFileExtensions(scriptFile.getName())) {
                var engine = manager.getEngineByExtension(ext);
                if (engine != null) {
                    return new ScriptingFun(this, dir, scriptFile, engine, options);
                }
            }

        }

        return null;
    }
}
