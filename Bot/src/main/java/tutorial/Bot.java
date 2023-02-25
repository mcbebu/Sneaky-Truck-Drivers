package tutorial;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    static {
        Stripe.apiKey = REDACTED;
    }
    @Override
    public String getBotUsername() {
        return "ninjatimeslotbot";
    }

    @Override
    public String getBotToken() {
        return REDACTED;
    }

    private String[] timings = new String[0];
    int timingSize;
    //the timings are initialised when the bot is created
    public void addTimings(String[] timings) {
        this.timings = timings;
        timingSize = timings.length;
    }

    //formatter1 displays as 23 Jan 2023
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd LLL yyyy");
    LocalDate currDate = LocalDate.now();
    @Override
    public void onUpdateReceived(Update update) {
        //obtain the telegram ID from the person ordering the parcel
        //long userId = update.getMessage().getFrom().getId();
        long userId = 0;
        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        }
        //long userId = REDACTED;
        System.out.println(update);
        String trackingNumber = "a04812894214E";
        String product = "Prism flat screen TV";
        if (update.hasCallbackQuery() && update.getCallbackQuery().getMessage().getText().startsWith("/pay")) {
            String text = update.getCallbackQuery().getMessage().getText();
            SendMessage message;
            try {
                String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", 1000); // Amount in cents
                chargeParams.put("currency", "usd");
                chargeParams.put("description", "Test Payment");
                chargeParams.put("source", "tok_visa"); // Replace with your actual Stripe token
                Charge charge = Charge.create(chargeParams);
                message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Payment successful!");
                String amount = text.split(" ")[1];
                message.setText("Your payment of $" + amount + " was successful! Thank you for using our service.");


            } catch (StripeException e) {
                message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Error processing payment: " + e.getMessage());

            }
            try {
                execute(message);
            } catch (TelegramApiException telegramApiException){
                throw new RuntimeException();
            }

        } else if (update.hasMessage() && update.getMessage().isCommand() && update.getMessage().getText().equals("/start")) {
            String message1 = String.format("Dear customer, your parcel has arrived at Ninjavan warehouse. Order no: %s", trackingNumber);
            sendMsg(message1, userId);

            String message2 = String.format("Would you like to specify a preferred timeslot on %s?\n" +
                    "Additional charges will apply.\n" +
                    "This message will expire in 2 hours.", currDate.plusDays(1).format(formatter1));
            sendMsg(message2, userId);
            timingVote(userId);
        } else if (update.hasCallbackQuery()) {
            respondToButton(update, userId);
        } else if (update.hasMessage() && update.getMessage().getText().startsWith("/pay")) {
            // Parse the payment amount from the message
            String[] parts = update.getMessage().getText().split(" ");
            int amount = Integer.parseInt(parts[1]);

            // Create a charge
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount * 100);
            chargeParams.put("currency", "sgd");
            chargeParams.put("description", "Payment for Telegram bot");
            chargeParams.put("source", "tok_visa");
            try {
                Charge charge = Charge.create(chargeParams);
                // Send a payment confirmation message to the user
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Your payment of $" + amount + " was successful! Thank you for using our service.");
                execute(message);
            } catch (StripeException | TelegramApiException e) {
                e.printStackTrace();
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("There was an error processing your payment. Please try again later.");
                try {
                    execute(message);
                } catch (TelegramApiException telegramApiException) {
                    telegramApiException.printStackTrace();
                }
            }

        } else {
            sendMsg("Invalid command or text :(", userId);
        }
    }

    //template to send a message to the consignee
    public void sendMsg(String message, Long userId){
        //todo: find tracking number;
        SendMessage sm = SendMessage.builder()
                .chatId(userId.toString())
                .text(message).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void timingVote(Long userId){
        //todo: find tracking number;

        var yes = InlineKeyboardButton.builder().text("Yes").callbackData("Yes").build();
        var no = InlineKeyboardButton.builder().text("No").callbackData("No").build();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(yes);
        rowInline.add(no);
        InlineKeyboardMarkup keyboard1 = InlineKeyboardMarkup.builder().keyboardRow(rowInline).build();
        SendMessage sm = SendMessage.builder().chatId(userId.toString())
                .parseMode("HTML").text("Do you want to choose a timeslot?")
                .replyMarkup(keyboard1).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void respondToButton(Update update, long userId) {
        //user pressed a button
        // Set variables
        String call_data = update.getCallbackQuery().getData();
        if (call_data.equals("Yes")) {
            respondToYes(update, userId);
        } else if (call_data.equals("No")) {
            respondToNo(update, userId);
        } else if (call_data.equals("9am - 12pm ($4)")) {
            //bot sends an update to itself to run the /pay
            String newText = "/pay 4";
            Message newMessage = update.getCallbackQuery().getMessage();
            newMessage.setText(newText);
            CallbackQuery q = update.getCallbackQuery();
            q.setMessage(newMessage);
            Update selfUpdate = update;
            selfUpdate.setCallbackQuery(q);
            onUpdateReceived(selfUpdate);
        } else if (call_data.equals("12pm - 3pm ($3)")) {
            String newText = "/pay 3";
            Message newMessage = update.getCallbackQuery().getMessage();
            newMessage.setText(newText);
            CallbackQuery q = update.getCallbackQuery();
            q.setMessage(newMessage);
            Update selfUpdate = update;
            selfUpdate.setCallbackQuery(q);
            onUpdateReceived(selfUpdate);
        } else if (call_data.equals("3pm - 6pm ($5)")) {
            String newText = "/pay 5";
            Message newMessage = update.getCallbackQuery().getMessage();
            newMessage.setText(newText);
            CallbackQuery q = update.getCallbackQuery();
            q.setMessage(newMessage);
            Update selfUpdate = update;
            selfUpdate.setCallbackQuery(q);
            onUpdateReceived(selfUpdate);
        } else if (call_data.equals("6pm - 9pm ($2)")) {
            String newText = "/pay 2";
            Message newMessage = update.getCallbackQuery().getMessage();
            newMessage.setText(newText);
            CallbackQuery q = update.getCallbackQuery();
            q.setMessage(newMessage);
            Update selfUpdate = update;
            selfUpdate.setCallbackQuery(q);
            onUpdateReceived(selfUpdate);
        }
    }

    public void respondToYes(Update update, long userId) {
        // user pressed Yes
        // Set variables
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        DeleteMessage deleteMsg = new DeleteMessage(chat_id, (int) message_id);
        // show consignee all the available timeslots

        try {
            execute(deleteMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //show buttons
        List<List<InlineKeyboardButton>> someButtons = new ArrayList<List<InlineKeyboardButton>>();
        for (int i = 0; i < timingSize; i++) {
            //display each button
            var builtButton = InlineKeyboardButton.builder().text(timings[i]).callbackData(timings[i]).build();
            someButtons.add(List.of(builtButton));
        }
        InlineKeyboardButton cancelButton = InlineKeyboardButton.builder().text("Cancel").callbackData("No").build();
        someButtons.add(List.of(cancelButton));
        InlineKeyboardMarkup keyboard2 = new InlineKeyboardMarkup(someButtons);
        //send the keyboard with the buttons to the user
        Long wrappedUserId = userId;
        SendMessage sm = SendMessage.builder().chatId(wrappedUserId.toString())
                .parseMode("HTML").text(String.format("Please select your preferred timeslot on %s:", currDate.plusDays(1).format(formatter1)))
                .replyMarkup(keyboard2).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void respondToNo(Update update, long userId) {
        //user pressed No
        // Set variables
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        LocalDate localDate = LocalDate.now().plusDays(2);
        //returns the date in 2 days in the format eg 25 Feb 2023
        new_message.setText("Your parcel will arrive on " + localDate.format(formatter1) + ".");
        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Long userId = 0L;
        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        }
        System.out.println(update);
        SendMessage sm = SendMessage.builder()
                .chatId(userId.toString())
                .text("error processing payment").build();
        return sm;

        /*if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.equals("/pay")) {
                try {
                    String chatId = update.getMessage().getChatId().toString();
                    Map<String, Object> chargeParams = new HashMap<String, Object>();
                    chargeParams.put("amount", 1000); // Amount in cents
                    chargeParams.put("currency", "usd");
                    chargeParams.put("description", "Test Payment");
                    chargeParams.put("source", "tok_visa"); // Replace with your actual Stripe token
                    Charge charge = Charge.create(chargeParams);
                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("Payment successful!");
                    return message;
                } catch (StripeException e) {
                    SendMessage message = new SendMessage();
                    message.setChatId(update.getMessage().getChatId());
                    message.setText("Error processing payment: " + e.getMessage());
                    return message;
                }
            }
        }
        catch(StripeException | TelegramApiException e){
            e.printStackTrace();
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("There was an error processing your payment. Please try again later.");
            try {
                execute(message);
            } catch (TelegramApiException telegramApiException) {
                telegramApiException.printStackTrace();
            }
        }*/
    }
}
