package pets;

public class Pet {
    private String name;
    private int level_now, level_max, love_now, love_max, hungry_now, hungry_max;

    public Pet(String name){  //只提供名字构造方法，用于初次启动程序
        this.name = name;
        this.level_now = 1;
        this.level_max = 2;
        this.love_now = 0;
        this.love_max = 100;
        this.hungry_now = 100;
        this.hungry_max = 100;
    }
    public Pet(String name, int level_now, int love_now, int hungry_now){  //提供所有参数，用于读档
        this.name = name;
        this.level_now = level_now;
        this.level_max = 2;
        this.love_now = love_now;
        this.love_max = 100;
        this.hungry_now = hungry_now;
        this.hungry_max = 100;
    }


    public int getLevel_max() { return level_max;}

    public int getLevel_now() {
        return level_now;
    }

    public String getName() {
        return name;
    }

    public int getHungry_max() {
        return hungry_max;
    }

    public int getHungry_now() { return hungry_now; }

    public int getLove_max() { return love_max; }

    public int getLove_now() {
        return love_now;
    }

    public void setLevel_now(int level_now) {
        this.level_now = level_now;
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

    public void increaseLove(int love){
        //增加亲密度的方法，love<0 代表降低亲密度
        if(this.love_now + love < 0){
            this.love_now = 0;
        } else if(this.love_now + love <= this.love_max){
            this.love_now += love;
        } else if(this.love_now + love > this.love_max){
            this.love_now = this.love_max;
        }

    }
}
