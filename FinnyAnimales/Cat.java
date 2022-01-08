package FinnyAnimales;

public class Cat extends Animal{
    static int catCount;
    static final int MAX_SWIM_DISTANCE = 0;
    static final int MAX_RUN_DISTANCE = 200;
    private String name;


    public Cat(String name){
        this.name = name;
        addCatCount();
        addAnimalsCount();
    }
    public String getName() {
        return name;
    }
    @Override
    public void run(int dist) throws AnimalException{
        if(dist > MAX_RUN_DISTANCE){
            AnimalException ex = new AnimalException(dist, Cat.MAX_RUN_DISTANCE,"cat " + this.name);
            throw ex;
        }
        System.out.println("Yes, I did this! Don't forgot my name: " + this.name + " I can run " + dist + "metres");
    }
    @Override
    public void swim(int dist) throws AnimalException{
        if(dist > MAX_SWIM_DISTANCE){
            AnimalException ex = new AnimalException(dist, Cat.MAX_SWIM_DISTANCE, "cat " + this.name);
            throw ex;
        }
    }

    private static void addCatCount(){
        ++catCount;
    }

    public static void prnCatNumber(){
        System.out.println("Всего создано " +  catCount + " забавных котиков");
    }


}
