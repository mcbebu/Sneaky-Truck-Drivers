import java.util.ArrayList;
import java.util.List;

public class fallback_code {

//    Hard coded implementation of time slots
    public void selectTimeSlots(Long userId){
        var nine = InlineKeyboardButton.builder().text("9-12 ($3)").callbackData("9").build();
        var twelve = InlineKeyboardButton.builder().text("12-3 ($6)").callbackData("12").build();
        var three = InlineKeyboardButton.builder().text("3-6 ($2)").callbackData("3").build();
        var six = InlineKeyboardButton.builder().text("6-9 ($5)").callbackData("6").build();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(nine);
        rowInline.add(twelve);
        rowInline.add(three);
        rowInline.add(six);
        InlineKeyboardMarkup keyboard1 = InlineKeyboardMarkup.builder().keyboardRow(rowInline).build();
        SendMessage sm = SendMessage.builder().chatId(userId.toString())
                .parseMode("HTML").text("Pick time slots below")
                .replyMarkup(keyboard1).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
