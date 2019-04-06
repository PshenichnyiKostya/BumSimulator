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

    private List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private Bum bum;
    private SendMessage sendMessage = new SendMessage();
    private ArrayList<MainButton> buttons;
    private ButName butName = new ButName();
    private ButStart butStart = new ButStart();
    private ButFood butFood = new ButFood();
    private ButShop butShop = new ButShop();
    private ButHealth butHealth = new ButHealth();
    private ButWork butWork = new ButWork();
    private ButStud butStud = new ButStud();
    private int priceBottle;

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        // setButtons(sendMessage);
        if (message != null && message.hasText()) {
            String s = message.getText();
            if ("/start".equals(s)) {
                bum = new Bum(100, 0, 500, 0, 0, 50, 50);
                buttons = new ArrayList<MainButton>();
                buttons.add(butName);
                buttons.add(butStart);
                buttons.add(butFood);
                buttons.add(butHealth);
                buttons.add(butShop);
                buttons.add(butWork);
                buttons.add(butStud);

                initializeButtons(butStart);
                setButtons(sendMessage);
                sendMsg(message, "Привет, тебя как звать, братан?");
            } else if (butStart.isPresed()) {
                priceBottle = (int) (Math.random() * 8) + 1;
                initializeButtons(butName);
                bum.setName(s);
                setButtons(sendMessage);
                sendMsg(message, s + " это серьезный район, тут делаются серьезные дела");
                getBumInfo(message);
            } else if ("Инфа".equals(s) && butName.isPresed()) {
                initializeButtons(butName);
                getBumFullInfo(message);
                getBumInfo(message);
            } else if ("Магазин".equals(s) && butName.isPresed()) {
                initializeButtons(butShop);
                setButtons(sendMessage);
                sendMsg(message, "Может эти деньги на пиво спустить?");
            } else if ("Еда/Выпивка".equals(s) && butName.isPresed()) {
                initializeButtons(butFood);
                setButtons(sendMessage);
                sendMsg(message, "Приятного аппетита");

            } else if ("Здоровье".equals(s) && butName.isPresed()) {
                initializeButtons(butHealth);
                setButtons(sendMessage);
                sendMsg(message, "Лучше на диване полежать, не?");

            } else if ("Работать".equals(s) && butName.isPresed()) {
                initializeButtons(butWork);
                setButtons(sendMessage);
                sendMsg(message, "Я на работе — никто не против");

            } else if ("Учиться".equals(s) && butName.isPresed()) {
                initializeButtons(butStud);
                setButtons(sendMessage);
                sendMsg(message, "Только не на ПОИТ!");
            } else if ("Продать бутылки".equals(s) && butName.isPresed()) {
                initializeButtons(butName);
                bum.setDays(bum.getMoney() + bum.getBottles() * priceBottle);
                incDay();
            } else if ("Назад".equals(s) && !butName.isPresed() && !butStart.isPresed()) {
                initializeButtons(butName);
                setButtons(sendMessage);
                sendMsg(message, "В меню!!!");
            }

        }
    }

    private void incDay() {
        bum.setDays(bum.getDays() + 1);
        setButtons(sendMessage);
        priceBottle = (int) (Math.random() * 8) + 1;
    }


    private void initializeButtons(MainButton button) {
        for (MainButton but : buttons) {
            but.setPresed(false);
        }
        button.setPresed(true);
    }

    private void setButtons(SendMessage message) {
        keyboardRows.clear();
        if (butStart.isPresed()) {
            setButton(message, "Сиплый");
            setButton(message, "Веселый");
            setButton(message, "Сипатый");
        }
        if (butName.isPresed()) {
            setButton(message, "Инфа");
            setButton(message, "Еда/Выпивка");
            setButton(message, "Здоровье");
            setButton(message, "Работать");
            setButton(message, "Учиться");
            setButton(message, "Магазин");
            setButton(message, "Продать бутылки");
        }
        if (butFood.isPresed()) {
            setButton(message, "Пожрать на помойке");
            setButton(message, "Шава");
            setButton(message, "Столовая в 4 корпусе БГУИР");
            setButton(message, "Пожениться(5000₽/день)");
            setButton(message, "Лидское пшеничное");
            setButton(message, "Бульбаш");
            setButton(message, "Вискарь");
            setButton(message, "Моет");
            setButton(message, "Пить в баре(1000$/день)");
            setButton(message, "Назад");
        }
        if (butWork.isPresed()) {
            setButton(message, "Бомжевать в метро");
            setButton(message, "Искать монеты");
            setButton(message, "Наехать на собутыльника");
            setButton(message, "Мести дворы");
            setButton(message, "Шурупить");
            setButton(message, "Прожить в ЕРАТЕ");
            setButton(message, "Управдять компанией");
            setButton(message, "Заседать в провительстве");
            setButton(message, "Управлять страной");
            setButton(message, "Назад");
        }
        if (butShop.isPresed()) {
            setButton(message, "Купить палатку");
            setButton(message, "Купить квартиру");
            setButton(message, "Купить офис");
            setButton(message, "Купить кросовки");
            setButton(message, "Купить велик");
            setButton(message, "Купить машину");
            setButton(message, "Назад");
        }
        if (butStud.isPresed()) {
            setButton(message, "Окончить школу");
            setButton(message, "Окончить ПТУ");
            setButton(message, "Окончить БНТУ");
            setButton(message, "Учиться за границей");
            setButton(message, "Назад");
        }
        if (butHealth.isPresed()) {
            setButton(message, "Таблетки с помойке");
            setButton(message, "Полечиться пивом");
            setButton(message, "Сходить к доктору");
            setButton(message, "Пойти в больницу");
            setButton(message, "Лечиться за границей");
            setButton(message, "Пробежка");
            setButton(message, "Записаться в качалку(15000₽/месяц)");
            setButton(message, "Назад");
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
        System.out.println("Asdasd");
        String string = "Это твои параметры, следи за собой" + " 👊" + "\n"
                + "Возраст ⏱" + "  " + bum.getDays() + "\n"
                + "Здоровье ❤️️" + "  " + bum.getHelth() + "\n"
                + "Еда 🍕" + "  " + bum.getFood() + "\n"
                + "Настроение 😁" + "  " + bum.getPleasure() + "\n"
                + "Бутылки 🍼" + "  " + bum.getBottles() + "\n"
                + "Деньги 💰" + "  " + bum.getMoney() + "\n"
                + "Курс 💸" + "  " + priceBottle;
        sendMsg(message, string);
    }

    private void getBumFullInfo(Message message) {
        String string = "Транспорт 🛸" + "  " + bum.getTransport() + "\n"
                + "Недвижимость 🏠" + "  " + bum.getHome() + "\n"
                + "Образование 🎓" + " " + bum.getEducation();
        sendMsg(message, string);
    }
}
