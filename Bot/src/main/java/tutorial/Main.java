package tutorial;
import java.util.HashMap;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Day day = new Day(10); // Assume only 10 parcels per slot
        HashMap<String, Integer> options = day.getOptions();
        int optionsSize = options.size();
        String[] timingOptions = options.keySet().toArray(new String[optionsSize]);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        bot.addTimings(timingOptions);
        botsApi.registerBot(new Bot());

    }
}
