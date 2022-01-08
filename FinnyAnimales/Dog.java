package FinnyAnimales;

public class Dog extends Animal{
    static int dogCount;
    static final int MAX_SWIM_DISTANCE = 10;
    static final int MAX_RUN_DISTANCE = 500;
    private String name;


    public Dog(String name){
        this.name = name;
        addDogCount();
        addAnimalsCount();
    }
    public String getName() {
        return name;
    }
    @Override
    public void run(int dist) throws AnimalException{
        if(dist > MAX_RUN_DISTANCE){
            AnimalException ex = new AnimalException(dist, Dog.MAX_RUN_DISTANCE,"dog " + this.name);
            throw ex;
        }
        System.out.println("Yes, I did this! Don't forgot my name: " + this.name + " I can run " + dist + "metres");
    }
    @Override
    public void swim(int dist) throws AnimalException{
        if(dist > MAX_SWIM_DISTANCE){
            AnimalException ex = new AnimalException(dist, Dog.MAX_SWIM_DISTANCE, "dog " + this.name);
            throw ex;
        }
        System.out.println("Yes, I did this! Don't forgot my name: " + this.name + " I can swim " + dist + "metres");
    }


    private static void addDogCount(){
        ++dogCount;
    }

    public static void prnDogNumber(){
        System.out.println("Всего создано " +  dogCount + " веселых глупых пёселей");
    }

}
