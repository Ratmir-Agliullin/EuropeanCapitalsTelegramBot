import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends TelegramLongPollingBot {
    private final String addressFileName = "C:\\Users\\agliullin\\Desktop\\idea projects\\EuropeanCapitalsTelegramBot\\src\\main\\resources\\buffer.dat";
    private final String textFileName = "C:\\Users\\agliullin\\Desktop\\idea projects\\EuropeanCapitalsTelegramBot\\src\\main\\resources\\text.dat";
    private final String subjectFileName = "C:\\Users\\agliullin\\Desktop\\idea projects\\EuropeanCapitalsTelegramBot\\src\\main\\resources\\subject.dat";
    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();
        Message message = update.getMessage();
   String answer =null;


                if (message == null) sendMsg(message, "Напиши страну Европы и я назову ее столицу"); else
                if (message != null) {
                    if (message.getText().equals("Франция")) {
                        answer = "Париж";
                        sendMsg(message, answer);
                    } else if (message.getText().equals("Германия") || message.getText().equals("ФРГ")) {
                        answer = "Берлин";
                        sendMsg(message, answer);
                      }  if(message.getText().equals("Inline")) {InlineKeyBoard(chat_id,update);}
                    else  if (update.getCallbackQuery().getData().equals("update")){
            sendMsg(message,"update");
                } else
                        {
                        String mas =message.getText();
                        String address =mas.split(" ")[0];
                        String text = mas.split(" ")[1];
                        String subject = mas.split(" ")[2];
                  sendMsg(message, address);
                  SendEmail(text,subject,address);
                    }
                }

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

    public void Serialize(String filePath, String text) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(text);
        out.close();
    }

    public String Deserialize(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream in =  new ObjectInputStream (new FileInputStream(filePath));
        String  result= in.readObject().toString();
        System.out.println(result);
        return result;
    }

    public void SendEmail(String text, String subj,String address){

        Sender sender = new Sender();
            sender.sendEMail(text,subj,address);


    }


    private void KeyBoard(long chat_id){

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("There is your keyboard");
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Send email");
        row.add("put address");
        row.add("put text");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("put subject");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void InlineKeyBoard(long chat_id, Update update){
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("You send /start");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Update").setCallbackData("update"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        if (update.getCallbackQuery().getData().equals("update")){
//            sendMsg(update.getMessage(),"update");
//        }
    }
}

