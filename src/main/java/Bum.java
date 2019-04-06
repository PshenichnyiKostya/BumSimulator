public class Bum {
    private String name;

    private int health;

    private int respect;

    private int money;

    private int bottles;

    private int days;

    private String transport="Босой 🦵";

    private String home="Нет ❌🏠❌";

    private String education="Поитовец ❌🧠❌";

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPleasure(int pleasure) {
        this.pleasure = pleasure;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getHealth() {
        return health;
    }

    public int getPleasure() {
        return pleasure;
    }

    public int getFood() {
        return food;
    }

    private int pleasure;

    private int food;

    public Bum(int health, int respect, int money, int bottles, int days, int pleasure,int food) {
        this.health = health;
        this.respect = respect;
        this.money = money;
        this.bottles = bottles;
        this.days = days;
        this.pleasure = pleasure;
        this.food=food;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHelth(int health) {
        this.health = health;
    }

    public void setRespect(int respect) {
        this.respect = respect;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHelth() {
        return health;
    }

    public int getRespect() {
        return respect;
    }

    public int getMoney() {
        return money;
    }

    public int getBottles() {
        return bottles;
    }

    public int getDays() {
        return days;
    }

    public String getName() {
        return name;
    }
}
