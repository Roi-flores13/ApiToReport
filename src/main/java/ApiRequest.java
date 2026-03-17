import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;

public class ApiRequest {

    private String apiKey;
    private OkHttpClient client;

    public ApiRequest(String apiKey){
        setApiKey(apiKey);
        this.client = new OkHttpClient();
    }

    public boolean standings(String leagueId, String season) throws Exception{
        // try block tries to send a request to the api
        try {
            Request request = new Request.Builder()
                    .url("https://www.thesportsdb.com/api/v1/json/" + this.apiKey +
                            "/lookuptable.php?l=" + leagueId + "&s=" + season) //Custom leagueID and season
                    .get()
                    .addHeader("accept", "application/json")
                    .build();

            Response response = this.client.newCall(request).execute(); //Create request GET
            String first = response.body().string(); //JSON response as a String

            JsonObject json = JsonParser.parseString(first).getAsJsonObject(); //Create String into a JsonObject
            JsonArray table = json.getAsJsonArray("table"); // Get the array inside the JSON that contains all the info

            for (JsonElement i : table) {
                JsonObject jsonTeam = i.getAsJsonObject();
                System.out.println(jsonTeam.get("strTeam").getAsString());
            }
        }

        catch (IOException e){
            System.out.println("Network error: " + e.getMessage());
            return false;
        }

        catch (NullPointerException e){
            System.out.println("Empty response: " + e.getMessage());
            return false;
        }

        catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            return false;
        }

        return true;
    }

    public String getApiKey(){
        return this.apiKey;
    }

    public void setApiKey(String apiKey){ //Falta agregarle reglas de negocio
        this.apiKey = apiKey;
    }
}

/*
League codes for each league:
      "idLeague": "4328",
      "strLeague": "English Premier League"

      "idLeague": "4329",
      "strLeague": "English League Championship"

      "idLeague": "4330",
      "strLeague": "Scottish Premier League"

      "idLeague": "4331",
      "strLeague": "German Bundesliga"

      "idLeague": "4332",
      "strLeague": "Italian Serie A"

      "idLeague": "4334",
      "strLeague": "French Ligue 1"

      "idLeague": "4335",
      "strLeague": "Spanish La Liga"

      "idLeague": "4336",
      "strLeague": "Greek Superleague Greece"

      "idLeague": "4337",
      "strLeague": "Dutch Eredivisie"

      "idLeague": "4338",
      "strLeague": "Belgian Pro League"
 */
