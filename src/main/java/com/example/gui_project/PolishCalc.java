package com.example.gui_project;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class PolishCalc {
     String Res (String inpt) {
        String numericExpression = inpt;

        String[] postfixExpression = convertToPostfix(numericExpression);

        System.out.println("Польская нотация: " + String.join(" ", postfixExpression));

        String result = evaluateExpression(postfixExpression);

        return result;
    }

    private static String[] convertToPostfix(String infixExpression) {
        Stack<String> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        StringTokenizer tokenizer = new StringTokenizer(infixExpression, "()+-*/^ ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            }

            if (isOperand(token)) {
                postfix.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop(); // Удаляем открывающую скобку
            } else {
                // Оператор
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(token)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim().split("\\s+");
    }

    private static boolean isOperand(String token) {
        // Проверяем, является ли токен операндом (числом)
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int getPrecedence(String operator) {
        // Возвращает приоритет оператора
        switch (operator) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0; // Низший приоритет для неизвестных операторов
        }
    }

    private static String evaluateExpression(String[] postfixExpression) {
        Stack<String> stack = new Stack<>();

        try {
            for (String token : postfixExpression) {
                if (isOperand(token)) {
                    // Если операнд, добавляем его в стек
                    stack.push(token);
                } else {
                    // Если оператор, выполняем соответствующую операцию
                    String operand2 = stack.pop();
                    String operand1 = stack.pop();
                    String result = performOperation(token, operand1, operand2);
                    stack.push(result);
                }
            }

            // В конце стек должен содержать результат выражения
            return stack.pop();
        } catch (EmptyStackException e) {
            return "Invalid expression";
        }
    }

    private static String performOperation(String operator, String operand1, String operand2) {
        // Выполняет операцию в зависимости от оператора
        try {
            double num1 = Double.parseDouble(operand1);
            double num2 = Double.parseDouble(operand2);

            switch (operator) {
                case "^":
                    return String.valueOf(Math.pow(num1, num2));
                case "*":
                    return String.valueOf(num1 * num2);
                case "/":
                    if (num2 == 0) {
                        return "Division by zero";
                    }
                    return String.valueOf(num1 / num2);
                case "+":
                    return String.valueOf(num1 + num2);
                case "-":
                    return String.valueOf(num1 - num2);
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректные операнды: " + operand1 + ", " + operand2);
        }
    }
}