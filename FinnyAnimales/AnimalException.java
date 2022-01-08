package FinnyAnimales;

public class AnimalException extends Exception{
    int max;
    int preset;
    String msg;

    public AnimalException(int max, int preset, String whichAnimal){
        this.max = max;
        this.preset = preset;
        msg = whichAnimal;
    }
    public String toString(){
        msg = "ОШИБКА: превышен допустимый предел для " + msg + " = " + preset + " на " + (max - preset);
        return msg;
    }
}
