package Home_Work_1;

public class HomeWorkApp {
    public static void main(String[] args){
        printThreeWords();
        checkSumSign(10,15);
        printColor(124);
        compareNumbers(3,0);
    }
    public static void printThreeWords(){
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }
    public static void checkSumSign(int a, int b){
        int sum = a + b;
        if(sum >= 0)
            System.out.println("Сумма = " + sum + ", это положительное значение");
        else
            System.out.println("Сумма = " + sum + ", это отрицательное значение");
    }
    public static void printColor(int color){
        if(color <= 0)
            System.out.println("числовое значение цвета = " + color + ", это Красный!");
        else if(color <= 100)
            System.out.println("числовое значение цвета = " + color + ", это Жёлтый!");
        else
            System.out.println("числовое значение цвета = " + color + ", это Зелёный!");
    }
    public static void compareNumbers(int a, int b){
        boolean res = (a >= b) ? true:false;
        if(res)
            System.out.println("a >= b");
        else
            System.out.println("a < b");
    }

}