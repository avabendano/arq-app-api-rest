package me.victoria.clima.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.victoria.clima.models.CharacterRM;

/**
 *
 * @author Victoria
 */
public class CharactersService {

    static String API_ENDPOINT = "https://rickandmortyapi.com/api";
    
    public static ArrayList<CharacterRM> getCharacters() {
        return getCharacters(1);
    }

    public static ArrayList<CharacterRM> getCharacters(int page) {
        Gson gson = new Gson();
        
        String endpoint = API_ENDPOINT + "/character";
        
        if (page > 1) {
            endpoint += String.format("?page=%d", page);
        }
        
        System.out.println(endpoint);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .GET()
                    .build();

            HttpResponse<String> res = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, BodyHandlers.ofString());

            System.out.println(res.body());

            JsonObject jsonObject = gson.fromJson(res.body(), JsonObject.class);

            System.out.println(jsonObject);
            var chars = jsonObject.getAsJsonArray("results");

            ArrayList<CharacterRM> characters = new ArrayList<>();

            chars.forEach(c -> {
                characters.add(new CharacterRM(
                        c.getAsJsonObject().get("id").getAsInt(),
                        c.getAsJsonObject().get("name").getAsString(),
                        c.getAsJsonObject().get("species").getAsString(),
                        c.getAsJsonObject().get("image").getAsString()
                ));
            });

            return characters;
        } catch (URISyntaxException ex) {
            Logger.getLogger(CharactersService.class.getName()).log(Level.SEVERE, "URI incorrecta", ex);
        } catch (IOException ex) {
            Logger.getLogger(CharactersService.class.getName()).log(Level.SEVERE, "Error al convertir la informacion", ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CharactersService.class.getName()).log(Level.SEVERE, "Tiempo excedido", ex);
        }

        return null;
    }
}
