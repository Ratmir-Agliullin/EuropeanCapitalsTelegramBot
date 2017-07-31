import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();
        Message message = update.getMessage();
   String answer ="Я не знаю что ответить на это";

   if (message.getText().equals("put address")) {
       try {
           Serialize(message.getText().toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       sendMsg(message,"put text");} else
   if (message.getText().equals("put text")) {
       try {
           Serialize(message.getText().toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       sendMsg(message,"put subject");} else
   if (message.getText().equals("put subject")) {
       try {
           Serialize(message.getText().toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       sendMsg(message,"send mail");}else
        if (message.getText().equals("/markup"))  KeyBoard(chat_id); else
        if (message.getText().equals("Send email")) { //SendEmail(buffer.getText(),buffer.getSubject(),buffer.getAddress());
            try {
                SendEmail(Deserialize()[0],Deserialize()[1],Deserialize()[2]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (message == null) sendMsg(message, "Напиши страну Европы и я назову ее столицу");
            if (message != null) {
                if (message.getText().equals("Франция"))
                    answer = "Париж";
                if (message.getText().equals("Германия") || message.getText().equals("ФРГ"))
                    answer = "Берлин";
            }

            sendMsg(message, answer);
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

    public void Serialize(String text) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\agliullin\\Desktop\\idea projects\\EuropeanCapitalsTelegramBot\\src\\main\\resources\\buffer.dat"));
        out.writeObject(text);
        out.close();
    }

    public String[] Deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in =  new ObjectInputStream (new FileInputStream("C:\\Users\\agliullin\\Desktop\\idea projects\\EuropeanCapitalsTelegramBot\\src\\main\\resources\\buffer.dat"));
        String [] result=null;
        result[0] = in.readObject().toString();
        result[1] = in.readObject().toString();
        result[2] = in.readObject().toString();
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
}
