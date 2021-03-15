package net.adriantodt.tinyfun.utils;

import com.grack.nanojson.JsonParser;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Util {
    public static final Pattern SLASH_PATTERN = Pattern.compile("/", Pattern.LITERAL);

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> Error justThrow(Throwable e) throws E {
        throw (E) e;
    }

    public static Map<String, Object> parseJsonFromFile(File file) {
        try (var input = new FileInputStream(file)) {
            return JsonParser.object().from(input);
        } catch (Exception e) {
            throw justThrow(e);
        }
    }

    private static final ThreadLocal<Yaml> threadLocalYaml = ThreadLocal.withInitial(Yaml::new);

    public static List<String> possibleFileExtensions(String name) {
        var index = name.indexOf('.');

        var result = new ArrayList<String>();

        while (index != -1) {
            if (index >= name.length()) {
                result.add("");
                break;
            }
            result.add(name.substring(index + 1));
            index = name.indexOf('.', index + 1);
        }

        return result;

    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseYamlFromFile(File file) {
        try (var input = new FileInputStream(file)) {
            return threadLocalYaml.get().loadAs(input, Map.class);
        } catch (Exception e) {
            throw justThrow(e);
        }
    }
}
