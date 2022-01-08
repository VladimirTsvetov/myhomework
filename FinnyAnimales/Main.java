package FinnyAnimales;

/*
1. Создать классы Собака и Кот с наследованием от класса Животное.
2. Все животные могут бежать и плыть. В качестве параметра каждому методу передается длина препятствия.
    Результатом выполнения действия будет печать в консоль. (Например, dogBobik.run(150); -> 'Бобик пробежал 150 м.');
3. У каждого животного есть ограничения на действия (бег: кот 200 м., собака 500 м.;
    плавание: кот не умеет плавать, собака 10 м.).
4. * Добавить подсчет созданных котов, собак и животных.
 */
public class Main {
    public static void main(String[] args) {
        Cat Tigra = new Cat("Тигра");
        Dog Bobik = new Dog("Бобик");
        try {
            Tigra.run(100);
        }catch (AnimalException e){
            System.out.println(e);
            System.out.println("Я кошка, и не думайте, что я побегу так далеко!");
        }
        try{
            Tigra.swim(10);
        }catch (AnimalException e){
            System.out.println(e);
            System.out.println("Вы серьёзно?! Мне плавать? Я кошка какбэ!!!");
        }
        try {
            Bobik.run(300);
        }catch (AnimalException e){
            System.out.println(e);
        }
        try {
            Bobik.swim(100);
        }catch (AnimalException e){
            System.out.println(e);
            System.out.println("Бобик сдох");
        }
        System.out.println("За время эксперимента было создано: " + Animal.getCount() + "  забавных зверюшек:");
        Cat.prnCatNumber();
        Dog.prnDogNumber();

    }

}
