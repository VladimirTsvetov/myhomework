package lesson7;

public class Cat {
    private final boolean HUNGRY = false;
    private final boolean SATIETY = true;

    private String name;
    private boolean isSatiety;
    private int appetit;

    public String getName() {
        return name;
    }

    public boolean isSatiety() {
        return isSatiety;
    }

    public int getAppetit() {
        return appetit;
    }

    public Cat(String name,int appetit){
        this.name = name;
        this.appetit = appetit;
        isSatiety = HUNGRY;
    }
    public void eating(Plate plate){
        try{
            plate.eatDecreasing(this.appetit);
            isSatiety = SATIETY;
            System.out.println(this.name + " Всем спасибо!");
        }catch(NoEatException ex){
            System.out.println("СРОЧНОЕ СООБЩЕНИЕ ОТ " + getName());
            System.out.println(ex.toString());
            isSatiety = HUNGRY;
        }
    }

}
