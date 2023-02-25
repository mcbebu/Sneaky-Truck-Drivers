package tutorial;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Day day = new Day(10); // Assume only 10 parcels per slot
        ArrayList<String> options = day.getOptions(); //retrieve the current options
        int optionsSize = options.size();
        String[] timingOptions = options.toArray(new String[optionsSize]); //create an array of timings
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(); //create a new bot
        bot.addTimings(timingOptions); //send the available timings to bot
        botsApi.registerBot(bot);
        System.out.println(System.getProperty("java.class.path"));
    }
}
