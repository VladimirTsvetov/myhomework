package lesson7;
/*
1. Расширить задачу про котов и тарелки с едой.
2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды
    (например, в миске 10 еды, а кот пытается покушать 15-20).
3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
    Если коту удалось покушать (хватило еды), сытость = true.
4. Считаем, что если коту мало еды в тарелке, то он её просто не трогает, то есть не может быть наполовину сыт
    (это сделано для упрощения логики программы).
5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки и потом вывести информацию
    о сытости котов в консоль.
6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку.
 */

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /* Cat[] cats = new Cat[3];

        cats[0] = new Cat("Барсик", 100);
        cats[1] = new Cat("Мурзик",88);
        cats[2] = new Cat("Тимофей", 250);
        */
        /************
         * Создаем массив котов
         */
        ArrayList<Cat> catList = new ArrayList<Cat>(3);
        catList.add(new Cat("Барсик", 100));
        catList.add(new Cat("Мурзик",88));
        catList.add(new Cat("Тимофей", 250));

        Plate plate = new Plate(200);
        /***
         * кормим котов и выводим сообщение о состоянии сытости каждого
         */
        System.out.println("До кормления котов Её величества"  + plate);

        for(Cat cat: catList) {
            cat.eating(plate);
            if (cat.isSatiety()) {
                System.out.println(cat.getName() + " cыт и доволен");
                System.out.println(plate );

            } else {
                System.out.println(cat.getName() + " голоден и глубоко несчастен");
                System.out.println(plate);
            }
        }
        /***
         * Добавляем в тарелку еды
         */
        System.out.println("в тарелку добавлено 300 еды");
        plate.loadFood(300);
        for (Cat cat: catList)
            if(!cat.isSatiety()){
                cat.eating(plate);
                System.out.println(plate);
            }


    }
}
