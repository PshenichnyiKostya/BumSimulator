import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private Actions actions = new Actions();
    private List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private Bum bum;
    private SendMessage sendMessage = new SendMessage();


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        // setButtons(sendMessage);
        if (message != null && message.hasText()) {
            String s = message.getText();
            if ("/start".equals(s)) {
                bum = new Bum(100, 0, 500, 0, 0, 50, 50);
                actions.setStart(true);
                actions.setName(false);
                actions.setFullInf(false);
                setButtons(sendMessage);
                sendMsg(message, "Привет, тебя как звать, братан?");
            }
            if (actions.isStart() && !"/start".equals(s)) {
                actions.setName(true);
                actions.setStart(false);
                actions.setFullInf(false);
                bum.setName(s);
                setButtons(sendMessage);
                sendMsg(message, s + " это серьезный район, тут делаются серьезные дела");
                getBumInfo(message);
            }
            if ("Инфа".equals(s)) {
                actions.setStart(false);
                actions.setName(false);
                actions.setFullInf(true);
                getBumFullInfo(message);
                getBumInfo(message);
            }
            if ("Продать бутылки".equals(s)) {
                actions.setStart(false);
                actions.setName(false);
                actions.setFullInf(false);

            }
        }
    }

    private void setButtons(SendMessage message) {
        keyboardRows.clear();
        if (actions.isStart()) {
            setButton(message, "Сиплый");
            setButton(message, "Веселый");
            setButton(message, "Сипатый");
        }
        if (actions.isName()) {
            setButton(message, "Инфа");
            setButton(message, "Еда/Выпивка");
            setButton(message, "Здоровье");
            setButton(message, "Работать");
            setButton(message, "Учиться");
            setButton(message, "Продать бутылки");
            setButton(message , "Магазин");
        }
    }

    public String getBotUsername() {
        return "Бомжара";
    }

    public String getBotToken() {
        return "763465259:AAH9nRgqXBMWggxbtCPDS9ozkZVe9Ur-mj8";
    }

    private void sendMsg(Message message, String string) {
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(string);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private void setButton(SendMessage sendMessage, String string) {
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        setName(keyboardFirstRow, string);
        keyboardRows.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private void setName(KeyboardRow keyboardButtons, String string) {
        keyboardButtons.add(new KeyboardButton(string));
    }

    private void getBumInfo(Message message) {

        String string = "Это твои параметры, следи за собой" + " 👊" + "\n"
                + "Возраст ⏱" + "  " + bum.getDays() + "\n"
                + "Здоровье ❤️️" + "  " + bum.getHelth() + "\n"
                + "Еда 🍕" + "  " + bum.getFood() + "\n"
                + "Настроение 😁" + "  " + bum.getPleasure() + "\n"
                + "Бутылки 🍼" + "  " + bum.getBottles() + "\n"
                + "Деньги 💰" + "  " + bum.getMoney();
        sendMsg(message, string);
    }

    private void getBumFullInfo(Message message) {
        String string = "Транспорт 🛸" + "  " + bum.getTransport() + "\n"
                + "Недвижимость 🏠" + "  " + bum.getHome() + "\n"
                + "Образование 🎓" + " " + bum.getEducation();
        sendMsg(message, string);
    }
}
