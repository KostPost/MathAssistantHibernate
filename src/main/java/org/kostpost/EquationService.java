package org.kostpost;

import java.util.Stack;

public class EquationService {

    public String checkEquation(String equation) {
        String formattedEquation = formatEquation(equation);
        if (validateEquation(formattedEquation)) {
            equation = formattedEquation;
            return equation;
        } else {
            throw new IllegalArgumentException("Некорректное уравнение: " + equation);
        }
    }

    public static String formatEquation(String equation) {
        // Удаляем пробелы
        equation = equation.replaceAll("\\s+", "");
        // Удаляем все недопустимые символы, оставляем только цифры и знаки +, -, *, /
        equation = equation.replaceAll("[^0-9x+-/*=]", "");
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

        String startValidSymbols = "-1234567890x ";
        if(!startValidSymbols.contains(Character.toString(equation.charAt(0)))){
            return false;
        }

        String endValidSymbols = "1234567890";
        if(!endValidSymbols.contains(Character.toString(equation.charAt(equation.length() - 1)))) {
            return false;
        }

        String ValidSymbols = "+-*/1234567890x=()";
        for (int i = 0; i < equation.length() - 1; i++) {

            if(!ValidSymbols.contains(Character.toString(equation.charAt(i))))
                return false;
        }

        if(equation.chars().filter(ch -> ch == '=').count() != 1)
            return false;

        if(equation.chars().filter(ch -> ch == 'x').count() <= 0)
            return false;


        for (int i = 0; i < equation.length() - 1; i++) {
            char currentChar = equation.charAt(i);
            char nextChar = equation.charAt(i + 1);

            if(currentChar == 'x' && nextChar == 'x')
                return false;

            if ((currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/')
                    && (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/')) {
                return false;
            }
        }
        return true;
    }

    public double resolveExpression(String equation) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < equation.length()) ? equation.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < equation.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm();
                    } else if (eat('-')) {
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) {
                        x *= parseFactor();
                    } else if (eat('/')) {
                        x /= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor();
                }
                if (eat('-')) {
                    return -parseFactor();
                }
                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(equation.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }




}
