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
        this.client = new OkHttpClient();
    }

    // Cambié el tipo de retorno de boolean a api.PartidoNBA para devolver el objeto útil
    public List<PartidoNBA> getYesterdayMatch(String date) {
        try {
            Request request = new Request.Builder()
                    .url("http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=" + date)
                    .get()
                    .build();

            Response response = this.client.newCall(request).execute();

            if (!response.isSuccessful() || response.body() == null) {
                System.out.println("Error en la respuesta del servidor.");
                return null;
            }

            String jsonString = response.body().string();
            JsonObject jsonRoot = JsonParser.parseString(jsonString).getAsJsonObject();

            return JsonMapper.parsearPartidosNba(jsonRoot);

        } catch (IOException e){
            System.out.println("Network error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}