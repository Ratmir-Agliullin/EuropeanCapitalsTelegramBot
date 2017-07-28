import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
     //   StringBuffer answer=new StringBuffer("Я не знаю что ответить на это");
String answer ="Я не знаю что ответить на это";
        if (message==null) sendMsg(message,"Напиши страну Европы и я назову ее столицу");
        if (message != null ) {
            if (message.getText().equals("Франция"))
              answer="Париж";
            if (message.getText().equals("Германия")||message.getText().equals("ФРГ"))
                answer="Берлин";}

                sendMsg(message, answer);

            }

    public String getBotUsername() {
        return "EuropeanCapitalsBot";
    }

    public String getBotToken() {
        return "418649589:AAFq3FRlpic0MoJP2v5Lf3W1M9-8MND1ZvQ";
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Main());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
