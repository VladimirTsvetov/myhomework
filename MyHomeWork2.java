VladimirTsvetov-patch-2
package home_work_2;
//my homework#2
public class MyHomeWork2 {
    public static void main(String[] args) {
        if(checkSumm(50,10))
            System.out.println("Сумма пределах [10:20]");
        else
            System.out.println("Сумма вне диапазона [10:20]");
        printSignOfNumber(-222);
        int a = 127;
        if(isPositiv(a))
            System.out.println("Число " + a + " положительное");
        else
            System.out.println("Число " + a + " отрицательное");
        printWord("Hello Java", -5);
        int year = 1601;
        if(isLeapYear(year))
            System.out.println(year+" високосный");
        else System.out.println(year+" обычный");

    }
    //задание 1
    public static boolean checkSumm(int a, int b){
        int summa = a+b;
        if(summa >= 10 && summa <= 20)return true;
        else return false;
    }
    //задание 2
    public static void printSignOfNumber(int num){
        if(num >= 0)
            System.out.println("Число " + num + " положительное, это ясно сразу:)");
        else
            System.out.println("Число " + num + " отрицательно,там же минус!");
    }
    //задание 3
    public static boolean isPositiv(int num){
        if(num >= 0)
            return true;
        else
            return false;
    }
    //задание 4
    public static void printWord(String str,int n){
        if(n >= 1)
            for(int i = 0; i < n; i++)
                System.out.println(str);
        else
            System.out.println("Проверьте пожалуйста данные, мы не можем напечатать строку меньше 1 раза");
    }
    //задание 5
    public static boolean isLeapYear(int year){
       if(year % 4 == 0 ){
           if((year % 400) == 0)return true;
           if(year % 100 == 0)return false;
           return true;
       }
       return false;
    }

}

