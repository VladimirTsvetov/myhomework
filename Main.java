package homework3;

import java.util.Scanner;

public class Main {
    private static int abs;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int array[] = {1,0,1,0,1,0,1,1,1,0,1,0,0,1,1,1};
        int[] array2 = {1,2,3,0,5,3,66,22,6,100};

        System.out.println("массив из 100 элементов int");
        arr100();

        System.out.println("меняем нули на единицы");
        System.out.println("изначальный массив");
        prnArr(array);
        change1to0(array);
        System.out.println();
        System.out.println("инвертированный массив");
        prnArr(array);

        System.out.println();
        System.out.println("выводим квадратную матрицу " );
        sqArr(10,1);

        System.out.println();
        System.out.println("выводим массив по заказу капризного юзверя");
        prnArr(retArr(10,8));

        System.out.println();
        System.out.println("ищем 6");
        prnArr(findSix(array2));

        System.out.println();
        System.out.println("ищем мимимум и максимум в массиве");
        prnArr(array2);
        System.out.println();
        minMax(array2);

        System.out.println();
        System.out.println("сдвигаем массив");
        prnArr(array2);
        System.out.println("Задайте размер сдвига: -n влево на n позиций, n - вправо");

        int shift = scan.nextInt();
        prnArr(shiftArr(shift, array2)); //выводим результат сдвига
        scan.close();

        System.out.println();
        System.out.println("Ищем равные полусуммы массива");
        prnArr(array);
        System.out.println();
        if(isHalfSum(array))
            System.out.println("Сумма слева равна сумме справа!");
        else
            System.out.println("Нет, не повезло");
    }

    /*
    Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
    С помощью цикла и условия заменить 0 на 1, 1 на 0;
    */
    public static void change1to0(int[] arr){
        for(int i = 0;i < arr.length;i++)
            arr[i] = (arr[i] == 0) ? 1:0;
    }
    // печать одномерного массива
    public static void prnArr(int[] array){
        for(int i = 0;i < array.length;i++)
            System.out.print(array[i] + " ");
    }
    /*
       Задать пустой целочисленный массив длиной 100. С помощью цикла заполнить его значениями 1 2 3 4 5 6 7 8 … 100;
    */
    public static void arr100(){
        int[] arr = new int[100];

        for(int i = 0; i < arr.length;i++){
            arr[i] = i+1;
            if(i % 10 == 0)
                System.out.println();
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    /*
    Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое), и
    с помощью цикла(-ов) заполнить его диагональные элементы единицами
    */
    public static void sqArr(int dim, int num){
        if(dim < 3){
            System.out.println("ну и зачем вам массив меньше 3х3?!");
            return;
        }
        int[][] arr = new int[dim][dim];
        for(int i = 0; i < dim; i++)
            for(int j = 0;j < dim;j++ ){
                if(i == j)
                    arr[i][j] = 1;
                arr[i][dim - (i+1)] = 1;
            }

        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(arr[i][j] + "  ");
            }
            System.out.println();
        }

    }
    /*
        Написать метод, принимающий на вход два аргумента: len и initialValue, и возвращающий одномерный массив
        типа int длиной len, каждая ячейка которого равна initialValue;
    */
    public static int[] retArr(int dim, int num){
        int[] arr = new int[dim];
        for(int i = 0; i < dim; i++)
            arr[i] = num;
        return arr;

    }
    /*
    Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2;
     */
    public static int[] findSix(int arr[]){
        for(int i = 0; i < arr.length; i++)
            if(arr[i] < 6)
                arr[i] *= 2;
        return arr;
    }
    /*
     * Задать одномерный массив и найти в нем минимальный и максимальный элементы ;
     */
    public static void minMax(int[] arr){
        int maxIndex = 0;
        int max = arr[0];
        int minIndex = 0;
        int min = arr[0];

        for(int i = 1; i < arr.length; i++)
            if(arr[i] > max){
                max = arr[i];
                maxIndex = i;
            }
        for(int i = 1; i < arr.length; i++)
            if(arr[i] < min){
                min = arr[i];
                minIndex = i;
            }
        System.out.println("Максимальный элемент - arr[" + maxIndex + "] = " + max);
        System.out.println("Минимальный элемент - arr[" + minIndex + "] = " + min);
    }
    /*
     * Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть true,
     * если в массиве есть место, в котором сумма левой и правой части массива равны.
     * РЕШЕНИЕ: ищем сумму всех элементов, потом начинаем суммировать элементы слева направо и проверять
     * делится ли вся сумма на расзность суммы и левой части ровно на 2. Если да, то мы нашли то заветное место
     */
    public static boolean isHalfSum(int[] arr){
        int sumR = 0;
        int sumL = 0;
        double res = 0;

        for(int i = 0; i < arr.length; i++)
            sumR += arr[i];
        System.out.println("Сумма всех элементов = " + sumR);
        for(int i = 0; i < arr.length; i++){
            sumL += arr[i];
            res = (double)sumR/(sumR - sumL);
            if(res == 2.00000000 && sumL != 0){
                System.out.println("сумма слева = " + sumL);
                return true;
            }
        }
        return false;
    }

    /*
     * Написать метод, которому на вход подается одномерный массив и число n
     * (может быть положительным, или отрицательным), при этом метод должен сместить все элементы массива на n позиций.
     * Элементы смещаются циклично. Для усложнения задачи нельзя пользоваться вспомогательными массивами.
     * Примеры: [ 1, 2, 3 ] при n = 1 (на один вправо) -> [ 3, 1, 2 ]; [ 3, 5, 6, 1] при n = -2 (на два влево) -> [ 6, 1, 3, 5 ].
     * При каком n в какую сторону сдвиг можете выбирать сами.
     *
     * РЕШЕНИЕ: при сдвиге больше чем размер массива отправляем шибко умного юзера лесом :)
     * иначе если сдвиг положительный, то запоминаем крайний правый элемент, переставляем в цикле все остальные на одну позицию
     * вправо, бывший правый элемент записываем самым левым. Так делаем ровно столько раз, каков наш сдвиг
     * для сдвига влево все зеркально
     */

    public static int[]  shiftArr(int shift, int[] arr){
        //печатаем массив
        if(Math.abs(shift) > arr.length){
            System.out.println("сдвиг слишком большой, тут вам не карусель :)");
            return arr;
        }
        int size = arr.length;
        int tmp_var = 0;
        if(shift > 0){
            for(int i = 0; i < shift; i++) {
                tmp_var = arr[size-1];
                for (int j = size - 1; j > 0; j--) {
                    arr[j] = arr[j - 1];
                }
                arr[0] = tmp_var;
            }
        }
        if(shift < 0)
        {
            shift = -1* shift;

            for(int i = 0; i < shift; i++) {
                tmp_var = arr[0];
                for (int j = 0; j < size-1; j++) {
                    arr[j] = arr[j + 1];
                }
                arr[size - 1] = tmp_var;
            }
        }
        return arr;
    }
}
