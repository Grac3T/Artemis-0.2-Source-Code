/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathematicalProcessor {
    private final Map<String, Integer> operators = new HashMap<String, Integer>();

    public MathematicalProcessor() {
        this.operators.put("-", 0);
        this.operators.put("+", 0);
        this.operators.put("/", 1);
        this.operators.put("*", 1);
        this.operators.put("^", 2);
    }

    public double doTheShuntingYard(String expression) throws IllegalArgumentException, NumberFormatException, ArithmeticException {
        if (expression == null || expression.trim().length() == 0) {
            throw new IllegalArgumentException("Empty expression or null");
        }
        expression = expression.replaceAll("\\s+", "");
        if ((expression = expression.replace("(-", "(0-")).startsWith("-")) {
            expression = "0" + expression;
        }
        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|([\\+\\-\\*\\/\\(\\)\\^]))");
        Matcher matcher = pattern.matcher(expression);
        int counter = 0;
        ArrayList<String> tokens = new ArrayList<String>();
        while (matcher.find()) {
            if (matcher.start() != counter) {
                throw new IllegalArgumentException("Invalid Expression:" + expression + ". Error between " + counter + " end " + matcher.start());
            }
            tokens.add(matcher.group().trim());
            counter += ((String)tokens.get(tokens.size() - 1)).length();
        }
        if (counter != expression.length()) {
            throw new IllegalArgumentException("Invalid end of expression");
        }
        Stack<String> stack = new Stack<String>();
        ArrayList<String> output = new ArrayList<String>();
        for (String token : tokens) {
            if (this.operators.containsKey(token)) {
                while (!stack.empty() && this.operators.containsKey(stack.peek()) && (this.operators.get(token) <= this.operators.get(stack.peek()) && !token.equals("^") || this.operators.get(token) < this.operators.get(stack.peek()) && token.equals("^"))) {
                    output.add((String)stack.pop());
                }
                stack.push(token);
                continue;
            }
            if (token.equals("(")) {
                stack.push(token);
                continue;
            }
            if (token.equals(")")) {
                while (!stack.empty() && !((String)stack.peek()).equals("(")) {
                    output.add((String)stack.pop());
                }
                if (stack.empty()) continue;
                stack.pop();
                continue;
            }
            output.add(token);
        }
        while (!stack.empty()) {
            output.add((String)stack.pop());
        }
        Stack<Double> doubles = new Stack<Double>();
        block21: for (String token : output) {
            if (!this.operators.containsKey(token) && token.matches("([0-9]*[.])?[0-9]+")) {
                doubles.push(Double.parseDouble(token));
                continue;
            }
            if (doubles.size() <= 1) continue;
            double op1 = (Double)doubles.pop();
            double op2 = (Double)doubles.pop();
            switch (token) {
                case "+": {
                    doubles.push(op2 + op1);
                    continue block21;
                }
                case "-": {
                    doubles.push(op2 - op1);
                    continue block21;
                }
                case "*": {
                    doubles.push(op2 * op1);
                    continue block21;
                }
                case "/": {
                    if (op1 == 0.0) {
                        throw new ArithmeticException("Division by 0");
                    }
                    doubles.push(op2 / op1);
                    continue block21;
                }
                case "^": {
                    doubles.push(Math.pow(op2, op1));
                    continue block21;
                }
            }
            throw new IllegalArgumentException(token + " is not an operator or is not handled");
        }
        if (doubles.empty() || doubles.size() > 1) {
            throw new IllegalArgumentException("Invalid expression, could not find a result. An operator seems to be absent");
        }
        return (Double)doubles.peek();
    }
}

