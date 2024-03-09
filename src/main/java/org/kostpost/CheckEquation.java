package org.kostpost;

import java.util.Stack;

public class CheckEquation {

    public String checkEquation(String equation) {
        String formattedEquation = formatEquation(equation);
        if (validateEquation(formattedEquation)) {
            equation = formattedEquation;
            return equation;
        }
        else if (!isValidDecimal(equation)) {
            return null;
        }else {
            return null;
        }
    }

    public static String formatEquation(String equation) {
        // Удаляем пробелы и разрешаем десятичные числа
        equation = equation.replaceAll("\\s+", "");
        equation = equation.replaceAll("[^0-9x.+-/*=()]", "");


        return equation;
    }


    boolean validateEquation(String equation) {
        if (!checkBrackets(equation)) {
            return false;
        }

        if (!isOperatorSequenceValid(equation)) {
            return false;
        }

        return true;
    }
    boolean isValidDecimal(String equation) {
        String[] tokens = equation.split("[=+-/*]"); // Разделяем уравнение на токены по операторам
        for (String token : tokens) {
            if (token.isEmpty()) {
                continue; // Пропускаем пустые токены, которые могут появиться из-за разделения
            }
            // Проверяем каждый токен на соответствие шаблону валидного десятичного числа или переменной
            if (!token.matches("x|\\d+\\.\\d+|\\d+")) {
                return false; // Невалидный токен
            }
        }
        return true; // Все токены валидны
    }


    boolean checkBrackets(String equation) {
        Stack<Character> stack = new Stack<>();

        for (char c : equation.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false; // Невірне закриття дужок
                }
            }
        }

        return stack.isEmpty(); // Повертає true, якщо всі дужки були коректно закриті
    }

    boolean isOperatorSequenceValid(String equation) {
        // Убедитесь, что уравнение содержит только один символ равенства
        if (equation.chars().filter(ch -> ch == '=').count() != 1) return false;

        // Проверка на корректное начало и конец уравнения
        String validStartSymbols = "-1234567890x(";
        if (!validStartSymbols.contains(Character.toString(equation.charAt(0)))) return false;

        String validEndSymbols = "1234567890x)";
        if (!validEndSymbols.contains(Character.toString(equation.charAt(equation.length() - 1)))) return false;

        // Проверка на корректное использование операторов и переменных
        for (int i = 0; i < equation.length() - 1; i++) {
            char currentChar = equation.charAt(i);
            char nextChar = equation.charAt(i + 1);

            // Проверка на двойные операторы, исключая "-",
            // так как "--" может быть частью валидного выражения (например, в "-x=-1")
            if ("+*/".indexOf(currentChar) >= 0 && "+-*/".indexOf(nextChar) >= 0) return false;

            // Условие, предотвращающее два 'x' подряд, может остаться, если это соответствует вашей логике
            if (currentChar == 'x' && nextChar == 'x') return false;
        }
        return true;
    }

}
