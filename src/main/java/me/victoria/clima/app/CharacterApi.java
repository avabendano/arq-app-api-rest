package me.victoria.clima.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Spark;
import me.victoria.clima.services.CharactersService;

/**
 *
 * @author Victoria
 */
public class CharacterApi {

    public static void main(String[] args) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        Spark.port(3001);

        Spark.init();

        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");

            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        Spark.get("/", (request, response) -> "Hello to my API");

        Spark.path("/api/characters", () -> {
            Spark.get("", (request, response) -> {
                response.type("application/json");

                var characters = CharactersService.getCharacters();

                // characters.forEach(System.out::println);
                return gson.toJson(characters);
            });

            Spark.get("/:page", (request, response) -> {
                response.type("application/json");

                int page = Integer.parseInt(request.params("page"));

                var characters = CharactersService.getCharacters(page);

                return gson.toJson(characters);
            });
        });

        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });
    }
}
