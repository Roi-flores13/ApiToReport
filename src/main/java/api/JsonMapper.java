package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/*
Clase estática encargada exclusivamente de analizar el árbol JSON y
traducirlo a las entidades de dominio.
 */

public class JsonMapper {

    public static List<PartidoNBA> parsearPartidosNba(JsonObject jsonRoot) {

        /*
        Método expuesto que itera sobre el arreglo de "events",
        orquestando la extracción de atributos y delegando objetos anidados a métodos auxiliares.
        Retorna la lista consolidada de partidos.
         */

        List<PartidoNBA> partidos = new ArrayList<>();

        try {
            // Iteramos sobre todos los "events" (Partidos) en lugar de solo el .get(0)
            JsonArray events = jsonRoot.getAsJsonArray("events");

            for (JsonElement eventoElement : events) {
                JsonObject evento = eventoElement.getAsJsonObject();

                String idEvento = evento.get("id").getAsString();
                String fecha = evento.get("date").getAsString();
                String estado = evento.getAsJsonObject("status")
                        .getAsJsonObject("type")
                        .get("description").getAsString();

                JsonObject competicion = evento.getAsJsonArray("competitions").get(0).getAsJsonObject();
                int asistencia = competicion.has("attendance") ? competicion.get("attendance").getAsInt() : 0;

                JsonArray competitors = competicion.getAsJsonArray("competitors");
                Equipo equipoLocal = null;
                Equipo equipoVisitante = null;

                for (JsonElement compElement : competitors) {
                    JsonObject competitor = compElement.getAsJsonObject();
                    Equipo equipo = mapearEquipo(competitor);

                    String localia = competitor.get("homeAway").getAsString();
                    if (localia.equals("home")) {
                        equipoLocal = equipo;
                    } else {
                        equipoVisitante = equipo;
                    }
                }

                // Agregamos el partido mapeado a nuestra lista
                partidos.add(new PartidoNBA(idEvento, fecha, estado, equipoLocal, equipoVisitante, asistencia));
            }
        } catch (Exception e) {
            System.err.println("Error al mapear el JSON: " + e.getMessage());
        }

        return partidos;
    }

    private static Equipo mapearEquipo(JsonObject competitor) {

        /*
        Construye una instancia de Equipo y determina si posee nodos de estadísticas y
        líderes para delegar su mapeo.
         */

        JsonObject teamNode = competitor.getAsJsonObject("team");

        String id = teamNode.get("id").getAsString();
        String nombre = teamNode.get("displayName").getAsString();
        String abreviacion = teamNode.get("abbreviation").getAsString();

        int score = Integer.parseInt(competitor.get("score").getAsString());
        boolean ganador = competitor.get("winner").getAsBoolean();

        Equipo equipo = new Equipo(id, nombre, abreviacion, score, ganador);

        if (competitor.has("statistics")) {
            equipo.setEstadisticas(mapearEstadisticas(competitor.getAsJsonArray("statistics")));
        }

        // NUEVO: Extraer los líderes
        if (competitor.has("leaders")) {
            equipo.setLideres(mapearLideres(competitor.getAsJsonArray("leaders")));
        }

        return equipo;
    }

    private static List<JugadorLider> mapearLideres(JsonArray leadersArray) {

        // Extrae el mejor jugador de cada categoría estadística y los agrupa en una lista.

        List<JugadorLider> listaLideres = new ArrayList<>();

        for (JsonElement element : leadersArray) {
            JsonObject categoryNode = element.getAsJsonObject();
            String categoria = categoryNode.get("displayName").getAsString(); // ej: "Points"

            JsonArray topLeaders = categoryNode.getAsJsonArray("leaders");
            if (topLeaders.size() > 0) {
                JsonObject topLeader = topLeaders.get(0).getAsJsonObject();
                String valor = topLeader.get("displayValue").getAsString();
                String nombreJugador = topLeader.getAsJsonObject("athlete").get("fullName").getAsString();

                listaLideres.add(new JugadorLider(categoria, nombreJugador, valor));
            }
        }
        return listaLideres;
    }

    private static Estadisticas mapearEstadisticas(JsonArray statsArray) {

        /*
        Itera sobre el sub-árbol de métricas y extrae los valores numéricos correspondientes mediante
        una estructura switch.
         */

        int pts = 0, reb = 0, ast = 0, tpm = 0;

        // El JSON de ESPN trae las estadísticas como un array de objetos {name, displayValue}
        for (JsonElement statElement : statsArray) {
            JsonObject stat = statElement.getAsJsonObject();
            String name = stat.get("name").getAsString();
            String value = stat.get("displayValue").getAsString();

            switch (name) {
                case "points":
                    pts = Integer.parseInt(value);
                    break;
                case "rebounds":
                    reb = Integer.parseInt(value);
                    break;
                case "assists":
                    ast = Integer.parseInt(value);
                    break;
                case "threePointFieldGoalsMade":
                    tpm = Integer.parseInt(value);
                    break;
            }
        }

        return new Estadisticas(reb, ast, pts, tpm);
    }
}