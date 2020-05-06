package pets;

public class Pet {
    private String name;
    private int level;
    private int love_now, love_max, hungry_now, hungry_max;

    public Pet(String name){  //只提供名字构造方法，用于初次启动程序
        this.name = name;
        this.level = 1;
        this.love_now = 0;
        this.love_max = 100;
        this.hungry_now = 100;
        this.hungry_max = 100;
    }
    public Pet(String name, int level, int love_now, int hungry_now){  //提供所有参数，用于读档
        this.name = name;
        this.level = level;
        this.love_now = love_now;
        this.love_max = 100;
        this.hungry_now = hungry_now;
        this.hungry_max = 100;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getHungry_max() {
        return hungry_max;
    }

    public int getHungry_now() {
        return hungry_now;
    }

    public int getLove_max() {
        return love_max;
    }

    public int getLove_now() {
        return love_now;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setHungry_now(int hungry_now) {
        this.hungry_now = hungry_now;
    }

    public void setLove_now(int love_now) {
        this.love_now = love_now;
    }
}
