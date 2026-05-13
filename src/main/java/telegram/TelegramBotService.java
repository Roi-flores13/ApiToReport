package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotService extends TelegramLongPollingBot implements Notificador{

    private final String botToken;
    private final String botUsername;

    public TelegramBotService(String botToken, String botUsername){
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    // Este método escucha los mensajes que le envían al bot (no lo usaremos ahora,
    // pero es requerido por la clase abstracta TelegramLongPollingBot)
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Mensaje recibido de " + update.getMessage().getChatId() + ": " + update.getMessage().getText());
        }
    }

    // Implementación de nuestra interfaz
    @Override
    public boolean enviarMensaje(String chatId, String mensaje) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(mensaje);
        sendMessage.setParseMode("HTML"); // Usaremos HTML para darle formato (negritas, cursivas) en Telegram

        try {
            execute(sendMessage); // Método heredado para enviar el mensaje a la API de Telegram
            return true;
        } catch (TelegramApiException e) {
            System.err.println("Error al enviar mensaje por Telegram: " + e.getMessage());
            return false;
        }
    }
}
