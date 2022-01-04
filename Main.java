package CardHolder;

/*
Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст.
Конструктор класса должен заполнять эти поля при создании объекта.
Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль.
Создать массив из 5 сотрудников.
Пример:
Person[] persArray = new Person[5]; // Вначале объявляем массив объектов
persArray[0] = new Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30);
// потом для каждой ячейки массива задаем объект
persArray[1] = new Person(...);
...
persArray[4] = new Person(...);
С помощью цикла вывести информацию только о сотрудниках старше 40 лет.

 */

import java.util.Scanner;

class Person{

    private String name;
    private String position;
    private String email;
    private String phone;
    private int salary;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, String position, String email, String phone, int salary, int age){
        setName(name);
        setPosition(position);
        setEmail(email);
        setPhone(phone);
        setSalary(salary);
        setAge(age);

    }
    public void printOut(){
        System.out.println("ФИО: " + getName());
        System.out.println("Должность: " + getPosition());
        System.out.println("E-MAIL: " + getEmail());
        System.out.println("Телефон: " + getPhone());
        System.out.println("Зарплата: " + getSalary());
        System.out.println("Возраст: " + getAge());

    }
}

public class Main {
    public static void main(String[] args) {
        String inp_name;
        String inp_position;
        String inp_email;
        String inp_phone;
        int inp_salary;
        int inp_age;
        int persons;

        System.out.println("Введите количество сотрудников");
        Scanner sc = new Scanner(System.in);
        persons = Integer.parseInt(sc.nextLine());

        Person[] pers = new Person[persons];



        for(int i = 0; i < pers.length; i++){
            System.out.println("Введите пожалуйста персональные данные сотрудника № " + (i + 1));

            do{
                System.out.print("ФИО: ");
                inp_name = inputName(sc);
                if(inp_name.lastIndexOf("ОШИБКА")  != -1){
                    System.out.println(inp_name);
                }
                else break;
            }while(true);

            do{
                System.out.print("должность: ");
                inp_position = inputPosition(sc);
                if(inp_position.lastIndexOf("ОШИБКА")  != -1){
                    System.out.println(inp_position);
                }
                else break;
            }while(true);

            do{
                System.out.print("e-mail: ");
                inp_email = inputEmail(sc);
                if(inp_email.lastIndexOf("ОШИБКА")  != -1){
                    System.out.println(inp_email);
                }
                else break;
            }while(true);

            do{
                System.out.print("номер телефона(11 цифр номера, начиная с 8): ");
                inp_phone = inputPhone(sc);
                if(inp_phone.lastIndexOf("ОШИБКА")  != -1){
                    System.out.println(inp_phone);
                }
                else break;
            }while(true);

            do{
                System.out.print("Зарплата: ");
                inp_salary = inputSalary(sc);
                if(inp_salary  == -1){
                    System.out.println("Мы не против, но рабство отменили.... Введите адекватную сумму");
                }
                if(inp_salary == -2){
                    System.out.println("Рома Абрамович, ты что ли?");
                }
                else break;
            }while(true);

            do{
                System.out.print("Возраст: ");
                inp_age = inputAge(sc);
                if(inp_age  == -1){
                    System.out.println("Дети и ветераны Куликовской битвы к работе не допускаются.");
                }
                else break;
            }while(true);

            System.out.println();

            pers[i] = new Person(inp_name,inp_position,inp_email,inp_phone,inp_salary,inp_age);
        }

        for(int i = 0; i < pers.length; i++)
            if(pers[i].getAge() > 40)
                pers[i].printOut();
        sc.close();
   }


    public static String inputPosition(Scanner scanner){
        String pos = scanner.nextLine();
        if(pos.isEmpty()){
            pos = "ОШИБКА ВВОДА: Введено пустое поле ДОЛЖНОСТЬ.";
        }
        return pos;
    }
    public static String inputName(Scanner scanner){
        String fio = scanner.nextLine();
        if(fio.isEmpty()){
            fio = "ОШИБКА ВВОДА: Введено пустое поле Ф.И.О.";
        }
        return fio;
    }

    public static String inputEmail(Scanner scanner){

        String mail = scanner.nextLine();
        if(mail.indexOf('@') < 1 ){
            mail = "ОШИБКА ВВОДА: адрес электронной почты должен содержать символ @ ";
        }
        if(mail.indexOf('.') < 0){
            mail += "ОШИБКА ВВОДА: пропущена точка в названии почты";
        }
        return mail;
    }
    public static String inputPhone(Scanner scanner){

        String phone = scanner.nextLine();
        char[] arr_phone = phone.toCharArray();

        if(phone.indexOf('8') != 0 ){
            phone = "ОШИБКА ВВОДА: номер телефона должен начинаться с 8 ";
            return phone;
        }
        if(arr_phone.length != 11){
            phone = "ОШИБКА ВВОДА: номер телефона должен содержать 11 цифр";
            return phone;
        }
        for(int i = 0; i < arr_phone.length;i++) {
            if (arr_phone[i] < '0' || arr_phone[i] > '9'){
                phone+= "ОШИБКА ВВОДА: телефон должен содержать только цифры от 0 до 9";
                return phone;
            }
        }
        return phone;
    }
    public static int inputSalary(Scanner scanner){
        String zp = scanner.nextLine();
        int salary;
        if(zp.length() > 8){
            return -2;
        }
         salary = Integer.parseInt(zp);
        if(salary <= 0) {
            return -1;
        }
        return salary;
    }
    public static int inputAge(Scanner scanner){
        String vozrast = scanner.nextLine();
        if(vozrast.length() > 4)
            return -1;
        int age = Integer.parseInt(vozrast);
        if(age <= 14 || age > 100) {
            return -1;
        }
        return age;
    }

}
