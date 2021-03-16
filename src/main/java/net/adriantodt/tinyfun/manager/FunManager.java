package net.adriantodt.tinyfun.manager;

import net.adriantodt.tinyfun.api.Fun;
import net.adriantodt.tinyfun.api.FunService;
import net.adriantodt.tinyfun.utils.Plumbing;
import net.adriantodt.tinyfun.utils.Util;
import net.adriantodt.tinyfun.utils.browser.DataBrowser;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunManager {
    private final File rootDir = new File("fun");
    private final Map<String, FunService> services;

    public FunManager() {
        this.services = ServiceLoader.load(FunService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(FunService::name, Function.identity()));
    }

    public void execute(String[] functions, InputStream inputStream, OutputStream outputStream) throws Exception {
        require(functions.length != 0, "Array of functions is empty");
        var funMap = loadFunctions();
        var funs = Arrays.stream(functions).map(funMap::get).collect(Collectors.toList());
        require(funs.stream().noneMatch(Objects::isNull), "Function not found");

        var length = funs.size();
        if (length == 1) {
            funs.get(0).execute(inputStream, outputStream);
            return;
        }

        // Pipe shenanigans
        var plumbing = new Plumbing(length, inputStream, outputStream);

        for (int i = 0; i < length; i++) {
            funs.get(i).execute(plumbing.input(i), plumbing.output(i));
        }
    }

    public Map<String, Fun> loadFunctions() {
        return Arrays.stream(Objects.requireNonNull(rootDir.listFiles()))
            .map(it -> {
                var handlerJson = new File(it, "handler.json");
                if (handlerJson.exists() && handlerJson.isFile()) {
                    var result = tryCreateFun(it, Util.parseJsonFromFile(handlerJson));
                    if (result != null) {
                        return result;
                    }
                }

                var handlerYml = new File(it, "handler.yml");
                if (handlerYml.exists() && handlerYml.isFile()) {
                    var result = tryCreateFun(it, Util.parseYamlFromFile(handlerYml));
                    if (result != null) {
                        return result;
                    }
                }

                var handlerYaml = new File(it, "handler.yaml");
                if (handlerYaml.exists() && handlerYaml.isFile()) {
                    return tryCreateFun(it, Util.parseYamlFromFile(handlerYaml));
                }

                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Fun::name, Function.identity()));
    }

    private Fun tryCreateFun(File runDir, Map<String, Object> options) {
        var opt = DataBrowser.create(options);

        var type = opt.get("type").safeText();
        var service = services.get(type);

        return service.load(runDir, options);
    }

    public static void require(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
