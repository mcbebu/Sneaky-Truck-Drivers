package tutorial;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class Bot extends TelegramLongPollingBot {



    @Override
    public String getBotUsername() {
        return "PunctualNinja_bot";
    }

    @Override
    public String getBotToken() {
        return "6040246735:AAGYVCTuyNpdnJ_6xRv0-N-SmPLCiY5c-bQ";
    }

    @Override
    public void onUpdateReceived(Update update) {


        // var msg = update.getMessage();
        // var user = msg.getFrom();
        //obtain the telegram ID from the person ordering the parcel
        long userId = 1238930495;
        System.out.println(update);
        String trackingNumber = "a04812894214E";
        if (update.getMessage().isCommand() && update.getMessage().getText().equals("/start")) {
            sendMsg(String.format("Dear customer, your parcel has arrived. Order no: %s", trackingNumber), userId);
        }
    }

    public void sendMsg(String msg, Long userId){
        //todo: find tracking number;
//        String firstMessage = String.format("Dear customer, your parcel has arrived. Order no: a04812894214E");
        SendMessage sm = SendMessage.builder()
                .chatId(userId.toString())
                .text(msg).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    
}
