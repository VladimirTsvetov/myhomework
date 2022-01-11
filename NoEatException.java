package lesson7;

public class NoEatException extends Exception{
    int food;
    int appetit;
    String message;

    public NoEatException(int food,int appetit,String msg){
        this.appetit = appetit;
        this.food = food;
        this.message = msg;
    }
    public String toString(){
        return message + "еды соталось " + food + " а аппетит " + appetit;
    }
}
