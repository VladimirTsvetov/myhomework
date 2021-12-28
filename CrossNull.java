package homework4;

import java.util.Scanner;

public class CrossNull {
    public static final char NULL_FIL = 'O';
    public static final char CROSS_FIL = 'X';
    public static final char DOT_FIL = '.';
    public static final int HUMAN_WIN = 1;
    public static final int II_WIN = 2;
    public static final int DRAW = 0;
    public static int SIZE;
    public static char[][]Map;
    public static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Давай поиграем в крестики-нолики!");
        System.out.println("Введите размер поля, но не менее 3");
        SIZE = scanner.nextInt();
        setMap(SIZE);
        drawMap(SIZE);
        while(true) {
            humanMove();
            drawMap(SIZE);
            if(checkWin(SIZE) == HUMAN_WIN)
            {
                System.out.println("Вы победили!");
                break;
            }
            if(isMapFull(SIZE)){
                System.out.println("Похоже ничья :)");
                break;
            }
            iiMove();
            drawMap(SIZE);
            if(checkWin(SIZE) == II_WIN){
                System.out.println("Ну кто бы сомневался! ИИ рулит");
                break;
            }
        }
        scanner.close();
    }

    /**************************************************************************
     * отрисовка карты
     * @param size определяет размер игрового поля
     * сначала печатаем шапу из чисел от 0 до указанного размера
     * потом хаполняем все поле символами из массива Map
     */
    public static void drawMap(int size){
        for(int i = 0; i < SIZE +1; i++)
            System.out.print(i + "  ");
        System.out.println();
        for(int i = 0; i < SIZE; i++) {
            System.out.print((i+1) + "  ");
            for (int j = 0; j < SIZE; j++)
                System.out.print(Map[i][j] + "  ");
            System.out.println();
        }
    }

    /***************************************************************************
     * инициализация массива Map символами '.', обозначающими незанятую клетку
     * @param size принимает значение размера игрового поля
     */
    public static void setMap(int size){
        Map = new char[size][size];
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                Map[i][j] = '.';
    }

    /****************************************************************************
     * Ход игрока. Вводим с клавиатуры позицию хода, если она не соответствует
     * размеру поля, то просим повторить ввод
     * если поле уже занято, то тоже повторяем ввод,
     * если поле свободно, то записываем в Map крестик
     */
    public static void humanMove(){
        int x;
        int y;
        System.out.println("Введите координаты поля в формате X,Y");
        while(true) {
            y = scanner.nextInt()-1;
            x = scanner.nextInt()-1;
            if ( x < 0 || y < 0 || x > SIZE-1 || y > SIZE-1) {
                System.out.println("Пожалуйста, будьте внимательны, Вы вышли за границы поля, попробуйте еще раз");
                continue;
            };
            if (checkMap(x, y)) {
                System.out.println("Пожалуйста, будьте внимательны, это поле занято, попробуйте еще раз");
                continue;
            }
            break;
        };
        Map[x][y] = CROSS_FIL;
    }
    /**********************************************************************************
     * Ход ИИ. Генерим 2 псевдослучайных число от 0 до установленного размера поля
     * проверяем, если поле занято, снова генерим пару чисел, если нет, записываем
     * в Map по этим координатам нолик
     */
    public static void iiMove(){
        System.out.println("Теперь я хожу");
        while(true){
            int x = (int)Math.round(Math.random()*(SIZE-1));
            int y = (int)Math.round(Math.random()*(SIZE-1));
            if(!checkMap(x,y)) {
                Map[x][y] = NULL_FIL;
                break;
            }
        };

    }

    /*********************************************************************************
     * проверка клеточки на занятость ноликом или крестиком
     * @param x позиция по горизонтали
     * @param y позиция по вертикали
     * @return возвращаем TRUE, если поле занято или FALSE, если свободно
     */

    public static boolean checkMap(int x,int y){
        return Map[x][y] == NULL_FIL || Map[x][y] == CROSS_FIL;
    }

    /**********************************************
     * проверка заполненности карты
     * @param map_size размер карты
     * @return false если хотя бы одно поле не заполнено ( == '.')
     */
    public static boolean isMapFull(int map_size){
        for(int i = 0; i < map_size; i++)
            for(int j = 0; j < map_size; j++)
                if(Map[i][j] == DOT_FIL)return false;
        return true;
    }

    /************************************************
     * проверка достижения победной позиции игроком или ИИ
     * @param map_size размер поля
     * @return код победителя
     * сначала проверяем диагонали, потом строки, потом столбцы на наличие одинаковых
     * символов. Если условие выполнено, то возвращаем код победителя
     */
    public static int checkWin(int map_size){
        //проверяем главную диагональ
        int posH = 0;
        int posI = 0;
        for(int i = 0, j = 0; i < SIZE; i++, j++){
            if(Map[i][j] == CROSS_FIL)
                posH++;
            if(Map[i][j] == NULL_FIL)
                posI++;
        }
        if(posH == SIZE)return HUMAN_WIN;
        if(posI == SIZE)return  II_WIN;
        //проверяем вторую
        //обнуляем счетчики
        posH = 0;
        posI = 0;
        for(int i = 0, j = SIZE-1; i < SIZE; i++, j--){
            if(Map[i][j] == CROSS_FIL)
                posH++;
            if(Map[i][j] == NULL_FIL)
                posI++;
        }
        if(posH == SIZE)return HUMAN_WIN;
        if(posI == SIZE)return  II_WIN;
        //проверяем строки
        for(int i = 0; i < SIZE; i++) {
            //обнуляем счетчики
            posH = 0;
            posI = 0;
            for (int j = 0; j < SIZE; j++) {
                if (Map[i][j] == CROSS_FIL)
                    posH++;
                if (Map[i][j] == NULL_FIL)
                    posI++;
            }
            if (posH == SIZE) return HUMAN_WIN;
            if (posI == SIZE) return II_WIN;
        }
        //проверяем столбцы
        for(int i = 0; i < SIZE; i++) {
            //обнуляем счетчики
            posH = 0;
            posI = 0;
            for (int j = 0; j < SIZE; j++) {
                if (Map[j][i] == CROSS_FIL)
                    posH++;
                if (Map[j][i] == NULL_FIL)
                    posI++;
            }
            if (posH == SIZE) return HUMAN_WIN;
            if (posI == SIZE) return II_WIN;
        }
        return DRAW;
    }
}
