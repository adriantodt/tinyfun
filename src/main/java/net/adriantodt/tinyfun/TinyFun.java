package net.adriantodt.tinyfun;

import com.grack.nanojson.JsonArray;
import net.adriantodt.tinyfun.manager.FunManager;
import net.adriantodt.tinyfun.utils.Util;

import java.io.ByteArrayInputStream;

import static spark.Spark.*;

public class TinyFun {
    public static void main(String[] args) {
        port(args.length > 0 ? Integer.parseInt(args[0]) : 8000);

        final var manager = new FunManager();

        post("/fun/*", (req, res) -> {
            final var splat = req.splat();
            if (splat.length == 0) {
                return null;
            }

            res.type("application/json");
            manager.execute(
                Util.SLASH_PATTERN.split(splat[0]),
                req.raw().getInputStream(),
                res.raw().getOutputStream()
            );

            return "";
        });
        get("/funs", (req, res) -> JsonArray.from(manager.loadFunctions().keySet().toArray()));
    }
}
