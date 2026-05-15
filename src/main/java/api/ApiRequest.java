package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiRequest {
    private OkHttpClient client;

    public ApiRequest(){
        this.client = new OkHttpClient(); // Creación del cliente para request
    }

    public List<PartidoNBA> getYesterdayMatch(String date) {
        /*
        Este metodo realiza un request a la API de ESPN, el cual regresa un JSON anidado de JSONs y lista,
        el cual incluye todas las estadísticas de la jornada de la fecha data.
        La API solo recibe 1 parametro: dates ("yyyymmdd")
         */
        try {
            Request request = new Request.Builder() // Creamos la estructura del request
                    .url("http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=" + date)
                    .get()
                    .build();

            Response response = this.client.newCall(request).execute(); // Ejecutamos request

            // Bloque if, en caso de que regrese vacio el request
            if (!response.isSuccessful() || response.body() == null) {
                System.out.println("Error en la respuesta del servidor.");
                return null;
            }

            String jsonString = response.body().string();
            JsonObject jsonRoot = JsonParser.parseString(jsonString).getAsJsonObject();

            return JsonMapper.parsearPartidosNba(jsonRoot); // Regresamos un objeto JSON

        } catch (IOException e){
            System.out.println("Network error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}