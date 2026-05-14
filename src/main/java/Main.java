import api.ApiRequest;
import api.PartidoNBA;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.Notificador;
import telegram.ReporteGenerator;
import telegram.TelegramBotService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("config.properties")){
            prop.load(input);
        } catch (IOException ex){
            System.err.println("No se encontró config.properties");
            return;
        }

        String botToken = prop.getProperty("botToken");
        String miChatId = prop.getProperty("miChatId");
        String botUsername = prop.getProperty("botUsername");

        LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fechaConsulta = hoy.format(formatter);

        try {
            // 1. Inicializar el motor de Telegram
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Notificador miBot = new TelegramBotService(botToken, botUsername);
            botsApi.registerBot((TelegramBotService) miBot); // Lo registramos para que funcione

            System.out.println("Bot de Telegram inicializado correctamente.");

            // 2. Extraer los datos de la API
            System.out.println("Consultando la API de ESPN...");
            ApiRequest api = new ApiRequest();
            List<PartidoNBA> jornada = api.getYesterdayMatch(fechaConsulta);

            // 3. Generar y enviar el reporte
            if (!jornada.isEmpty()) {
                String reporteHtml = ReporteGenerator.generarResumenTelegram(jornada);

                System.out.println("Enviando reporte a Telegram...");
                boolean enviado = miBot.enviarMensaje(miChatId, reporteHtml);

                if (enviado) {
                    System.out.println("¡Reporte enviado con éxito a Telegram!");
                } else {
                    System.out.println("Hubo un fallo al enviar el mensaje.");
                }
            } else {
                System.out.println("No se encontraron partidos.");
            }

        } catch (Exception e) {
            System.err.println("Error fatal en el programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}