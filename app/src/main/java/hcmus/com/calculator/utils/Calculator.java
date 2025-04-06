package hcmus.com.calculator.utils;

public class Calculator {
    public static double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        double result = handleMultiplicationAndDivision(tokens);
        return handleAdditionAndSubtraction(tokens, result);
    }

    private static double handleMultiplicationAndDivision(String[] tokens) {
        double result = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("*")) {
                result *= Double.parseDouble(tokens[i + 1]);
                i++;
            } else if (token.equals("/")) {
                result /= Double.parseDouble(tokens[i + 1]);
                i++;
            }
        }
        return result;
    }

    private static double handleAdditionAndSubtraction(String[] tokens, double initialResult) {
        double result = initialResult;
        for (int i = 1; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("+")) {
                result += Double.parseDouble(tokens[i + 1]);
                i++;
            } else if (token.equals("-")) {
                result -= Double.parseDouble(tokens[i + 1]);
                i++;
            }
        }
        return result;
    }
}
