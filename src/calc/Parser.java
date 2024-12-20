package calc;

import java.util.Stack;
import static java.lang.Character.*;
public class Parser {

    String infixExpression;
    String postFixExpression;
    Parser(String infixExpression) {
        infixExpression = infixExpression.toLowerCase().replaceAll(" ", "");

        //the formated expression goes through validation to root out obviously wrong expressions
        ValidityChecks.Valid(infixExpression);
        //received expression will first be preFormated to ease conversion to postFix
        this.infixExpression = prepFormat(infixExpression);
        this.postFixExpression = convertToPostfix();
    }


    // method to convert infix to postfix
    private String prepFormat(String infixExpression) {
        //standardize the mathematical expressions,
        //no space and all lowercase
            StringBuilder br = new StringBuilder();
            for (int i = 0; i < infixExpression.length(); i++) {
                //isSpecial checks if the char is cos,sin etc.,
                //which will be modified to make calculation easier
                //cos becomes c,sin becomes s etc., sqrt will become q to avoid confusion with sin
                char currentChar = infixExpression.charAt(i);
                if (isSpecial(currentChar)) {
                    if(i-1 >= 0 && isDigit(infixExpression.charAt(i-1))){
                        br.append('*');
                    }
                    if (currentChar == 'π') {
                        br.append("("+Math.PI+")");
                        if (i+1 <infixExpression.length() && isDigit(infixExpression.charAt(i+1))){
                            br.append("*");
                        }
                        continue;
                    }
                    // read the 3 char then switch to find out the operation
                    switch (String.valueOf(infixExpression.charAt(i++)) + infixExpression.charAt(i++) + infixExpression.charAt(i++)) {
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
                        case "tan":
                            br.append('t');
                            i--;
                            break;
                        default:
                            //if none of the 4 character operations it's sqrt
                            br.append('q');
                            break;
                    }
                } else if(i-1 >= 0 && (currentChar == '(') && isDigit(infixExpression.charAt(i - 1))){
                    br.append('*');
                }
                else if ((currentChar == '.')) {
                    if (i-1 >= 0){
                        if (!isDigit(infixExpression.charAt(i-1))){
                            br.append("0.");
                        }else{
                            br.append('.');
                        }
                    }else{
                        br.append("0.");
                    }
                }
                else if (currentChar == '-') {
                    if(infixExpression.charAt(i+1) == '-'){
                        i++;
                        br.append('+');
                    }
                    else if(i-1 <0){
                        i = appendNegativeNum(infixExpression, br, i);
                    }else{
                        if (!isDigit(infixExpression.charAt(i-1)) && infixExpression.charAt(i-1) != ')'){
                            i = appendNegativeNum(infixExpression, br, i);
                        }else{
                            br.append("-");
                        }
                    }
                } else {
                    //what doesn't need change goes straight to final prepared string
                    br.append(currentChar);
                }
            }
            return br.toString();
    }

    private int appendNegativeNum(String infixExpression, StringBuilder br, int i) {
        br.append("(0-");
//        while(i+1 < infixExpression.length() && ( infixExpression.charAt(i+1) == '.' || isDigit(infixExpression.charAt(i+1)) ) ){
        while(i+1 < infixExpression.length() && !isOperator(infixExpression.charAt(i+1))){
            i++;
            br.append(infixExpression.charAt(i));
        }
        br.append(")");
        return i;
    }

    private String convertToPostfix() {
        //convert the prepared expression to postFix to calculate
        //12+2 will become (12)(2)+
        // l(2*4) will become (2)(4)*l, l is log, as was changed in the prepFormat function
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
                operatorStack.push(current);
                }
                // If current character is a closing parenthesis
                else if (current == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                    }
                // Remove the opening parenthesis
                    if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                    }
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
     protected static boolean isSpecial(char ch) {
        return switch (ch){
            case 's', 'e', 'c', 'l', 'q', 't','π' -> true;
            default -> false;
        };
    }
     protected static boolean isOperator(char ch) {
        return switch (ch){
            case '+', '-', '*', '/','^' -> true;
            default -> false;
        };
    }

    // Method to get operator precedence
    private static int getPrecedence(char operator) {
        return switch (operator) {
            case 's', 'e', 'c', 'l', 'q' -> 4;
            case '^' -> 3;
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            default -> 0;
        };
    }

    public String getPostFixExpression() {
        return postFixExpression;
    }
}
