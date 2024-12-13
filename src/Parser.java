import java.util.Stack;
import static java.lang.Character.*;

public class Parser {
    String infixExpression;
    ValidityChecks checks;
    Parser(String infixExpression) {
        this.infixExpression = prepFormat(infixExpression);
        checks = new ValidityChecks(this.infixExpression);
    }

    // Main method to convert infix to postfix
    public String convertToPostfix() {
        if (!checks.Valid()){
            return null;
        }
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char current = infixExpression.charAt(i);

            // If current character is a digit, add to output
            if (Character.isDigit(current) || current == '.') {
                // Handle multi-digit numbers
                StringBuilder number = new StringBuilder(String.valueOf(current));
                while (i + 1 < infixExpression.length() &&
                        (Character.isDigit(infixExpression.charAt(i + 1)) || infixExpression.charAt(i + 1) == '.')) {
                    number.append(infixExpression.charAt(++i));
                }
                postfix.append("(").append(number).append(")");
            }
            // If current character is an opening parenthesis, push to stack
            else if (current == '(') {
//                operatorStack.push(current);
            }
            // If current character is a closing parenthesis
            else if (current == ')') {
//                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
//                    postfix.append(operatorStack.pop());
//                }
//                // Remove the opening parenthesis
//                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
//                    operatorStack.pop();
//                }
            }
            // If current character is an operator
            else if (isOperator(current) || isSpecial(current)) {
                while (!operatorStack.isEmpty() &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(current)) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(current);
            }
        }

        // Pop remaining operators from the stack
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }
    private String prepFormat(String infixExpression){
        //standardize the mathematical expressions,
        //no space and all lowercase
        StringBuilder br = new StringBuilder();
        for (int i = 0; i < infixExpression.length(); i++) {
            if (isUpperCase(infixExpression.charAt(i))) {
                br.append(toLowerCase(infixExpression.charAt(i)));
            }else if (!isSpaceChar(infixExpression.charAt(i))) {
                br.append(infixExpression.charAt(i));
            }
        }
        infixExpression = br.toString();
        br = new StringBuilder();
        for (int i = 0; i < infixExpression.length(); i++) {
            if (!(isDigit(infixExpression.charAt(i)) || isOperator(infixExpression.charAt(i))
                    || infixExpression.charAt(i) == '(' || infixExpression.charAt(i) == ')')) {
                StringBuilder tmp = new StringBuilder();
                for (int j = 0; j < 3; j++) {
                    tmp.append(infixExpression.charAt(i));
                    i++;
                }
                switch (tmp.toString()){
                    case "sin":
                        br.append('s');
                        i--;
                        break;
                    case "exp":
                        br.append('e');
                        i--;
                        break;
                    case "cos":
                        br.append('c');
                        i--;
                        break;
                    case "log":
                        br.append('l');
                        i--;
                        break;
                    default:
                        tmp.append(infixExpression.charAt(i));
                        if (tmp.toString().equals("sqrt")){
                            br.append('q');
                        break;
                        }else {
                            br.append(tmp);
                        }
                }
            }else{
                br.append(infixExpression.charAt(i));
            }
        }
        return br.toString();
    }
     static boolean isSpecial(char ch) {
        return switch (ch){
            case 's', 'e', 'c', 'l', 'q' -> true;
            default -> false;
        };
    }
     static boolean isOperator(char ch) {
        return switch (ch){
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }

    // Method to get operator precedence
    private static int getPrecedence(char operator) {
        return switch (operator) {
            case 's', 'e', 'c', 'l', 'q' -> 3;
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            default -> 0;
        };
    }

}
