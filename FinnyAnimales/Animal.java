package FinnyAnimales;

public abstract class Animal {
    static int count;
    //protected String name;
    public static void addAnimalsCount(){
       ++count;
    }
    public static void prnAnimalCount(){
        System.out.println("Всего создано " +  count + " забавных зверюшек");
    }
    public abstract void run(int distance) throws AnimalException;
    public abstract void swim(int distance) throws AnimalException;

}
