package homework8.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CalcWindow extends JFrame {
    private int value;
    private double leftOperand;
    private double rightOperand;
    private double result;
    private String operation = new String();
    private StringBuilder inputStr = new StringBuilder();
    private void InitButton(){

    }

    public CalcWindow(int initialValue) {
        setBounds(500,500,300,300);
        setTitle("Manual GUI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        JTextField field = new JTextField();                      // текстовое поле
        field.setBounds(20, 20, 120, 40);
        add(field);
        /**
         * формируем цифровые кнопки  калькулятора
         */

        JButton button7 = new JButton("7");
        button7.setBounds(20, 60, 40, 40);
        add(button7);
        JButton button4 = new JButton("4");
        button4.setBounds(20, 100, 40, 40);
        add(button4);
        setVisible(true);
        JButton button1 = new JButton("1");
        button1.setBounds(20, 140, 40, 40);
        add(button1);
        JButton button0 = new JButton("0");
        button0.setBounds(20, 180, 80, 40);
        add(button0);
        JButton button8 = new JButton("8");
        button8.setBounds(60, 60, 40, 40);
        add(button8);
        JButton button5 = new JButton("5");
        button5.setBounds(60, 100, 40, 40);
        add(button5);
        JButton button2 = new JButton("2");
        button2.setBounds(60, 140, 40, 40);
        add(button2);
        JButton button9 = new JButton("9");
        button9.setBounds(100, 60, 40, 40);
        add(button9);
        JButton button6 = new JButton("6");
        button6.setBounds(100, 100, 40, 40);
        add(button6);
        JButton button3 = new JButton("3");
        button3.setBounds(100, 140, 40, 40);
        add(button3);
        /**
         * командные кнопки калькулятора
         */
        JButton buttonZ = new JButton(",");
        buttonZ.setBounds(100, 180, 40, 40);
        add(buttonZ);
        JButton buttonD = new JButton("/");
        buttonD.setBounds(140, 60, 40, 40);
        add(buttonD);
        JButton buttonM = new JButton("*");
        buttonM.setBounds(180, 60, 40, 40);
        add(buttonM);
        JButton buttonDec = new JButton("-");
        buttonDec.setBounds(140, 100, 80, 40);
        add(buttonDec);
        JButton buttonInc = new JButton("+");
        buttonInc.setBounds(140, 140, 80, 40);
        add(buttonInc);
        JButton buttonRes = new JButton("=");
        buttonRes.setBounds(140, 180, 80, 40);
        add(buttonRes);
        JButton buttonCleare = new JButton("C");
        buttonCleare.setBounds(140, 20, 80, 40);
        add(buttonCleare);
        /**
         * определяем слушателей для кнопок **********************************
         */
        /**
         * КНОПКА 7
         */
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("7");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 8
         */
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("8");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 9
         */
        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("9");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 4
         */
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("4");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 5
         */
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("5");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 6
         */
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("6");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 1
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("1");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 2
         */
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("2");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 3
         */
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("3");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА 0
         */
        button0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.append("0");
                field.setText(inputStr.toString());
            }
        });
        /**
         * КНОПКА CLEAR (ОЧИСТКА БУФЕРА)
         */
        buttonCleare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputStr.delete(0,inputStr.length());
                field.setText("");
            }
        });
        /**
         * КНОПКА ПЛЮС (СЛОЖЕНИЕ)
         */
        buttonInc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               if(!inputStr.isEmpty()){
                   leftOperand = Double.parseDouble(inputStr.toString());
                   operation = "+";
                   inputStr.delete(0,inputStr.length());
                   field.setText("");
               }
            }
        });
        /**
         * КНОПКА МИНУС (ВЫЧИТАНИЕ)
         */
        buttonDec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!inputStr.isEmpty()){
                    leftOperand = Double.parseDouble(inputStr.toString());
                    operation = "-";
                    inputStr.delete(0,inputStr.length());
                    field.setText("");
                }
            }
        });
        /**
         * КНОПКА ДЕЛИТЬ
         */
        buttonD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!inputStr.isEmpty()){
                    leftOperand = Double.parseDouble(inputStr.toString());
                    operation = "/";
                    inputStr.delete(0,inputStr.length());
                    field.setText("");
                }
            }
        });
        /**
         * КНОПКА УМНОЖЕНИЯ
         */
        buttonM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!inputStr.isEmpty()){
                    leftOperand = Double.parseDouble(inputStr.toString());
                    operation = "*";
                    inputStr.delete(0,inputStr.length());
                    field.setText("");
                }
            }
        });
        /**
         * КНОПКА РАВНО (РЕЗУЛЬТАТ)
         */
        buttonRes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!inputStr.isEmpty()){
                    rightOperand = Double.parseDouble(inputStr.toString());
                    inputStr.delete(0,inputStr.length());
                    switch (operation){
                        case "+":
                            result = leftOperand + rightOperand;
                            inputStr.append(result);
                            break;
                        case "-":
                            result = leftOperand - rightOperand;
                            inputStr.append(result);
                            break;
                        case "/":
                            try{
                                result = leftOperand / rightOperand;
                                inputStr.append(result);
                            }
                            catch (ArithmeticException e) {
                                inputStr.append("Error: " + e.getMessage());
                            }
                            break;
                        case "*":
                            result = leftOperand * rightOperand;
                            inputStr.append(result);
                            break;
                    }
                    field.setText(inputStr.toString());
                }
            }
        });



    }
        /*setBounds(500, 500, 300, 120);
        setTitle("Simple Counter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 32);

        JLabel counterValueView = new JLabel();
        counterValueView.setFont(font);
        counterValueView.setHorizontalAlignment(SwingConstants.CENTER);
        add(counterValueView, BorderLayout.CENTER);

        value = initialValue;
        counterValueView.setText(String.valueOf(value));

        JButton decrementButton = new JButton("<");
        decrementButton.setFont(font);
        add(decrementButton, BorderLayout.WEST);

        JButton incrementButton = new JButton(">");
        incrementButton.setFont(font);
        add(incrementButton, BorderLayout.EAST);

        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                value--;
                counterValueView.setText(String.valueOf(value));
            }
        });

        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                value++;
                counterValueView.setText(String.valueOf(value));
            }
        });

        setVisible(true);
    }*/

    public static void main(String[] args) {
        new CalcWindow(0);
    }
}
