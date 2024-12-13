import java.util.Stack;
import static java.lang.Character.*;
public class Parser {
    Exception invalid = new Exception("expression is invalid");

    String infixExpression;
    ValidityChecks checks;
    Parser(String infixExpression) throws Exception {
//        try{
        //if expression is empty or null stop
        if (infixExpression == null || infixExpression.isEmpty()){
            throw invalid;
        }
        //received expression will first be preFormated to ease conversion to postFix
        this.infixExpression = prepFormat(infixExpression);
        //the formated expression goes through validation to root out obviously wrong expressions
         if (!ValidityChecks.Valid(this.infixExpression)){
             throw invalid;
         }
    }

    // method to convert infix to postfix
    private String prepFormat(String infixExpression) throws Exception {
        //standardize the mathematical expressions,
        //no space and all lowercase
            StringBuilder br = new StringBuilder();
            for (int i = 0; i < infixExpression.length(); i++) {
                if (isUpperCase(infixExpression.charAt(i))) {
                    br.append(toLowerCase(infixExpression.charAt(i)));
                } else if (!isSpaceChar(infixExpression.charAt(i))) {
                    br.append(infixExpression.charAt(i));
                }
            }
            infixExpression = br.toString();

            //the string that will receive the final prepared expression
            br = new StringBuilder();

            for (int i = 0; i < infixExpression.length(); i++) {
                //needsModification checks if the char is not
                //a digit, an operator, parentheses or a dot
                //if it's none of those it means it has to be a special operation
                //like cos,sin etc., which will be modified to make calculation easier
                //cos becomes c,sin becomes s etc., sqrt will become q to avoid confusion with sin
                if (needsModification(infixExpression.charAt(i))) {
                    //initialize a temporary stringBuilder
                    StringBuilder tmp = new StringBuilder();

                    // read the 3 char then switch to find out the operation
                    for (int j = 0; j < 3; j++) {
                        tmp.append(infixExpression.charAt(i));
                        i++;
                    }
                    switch (tmp.toString()) {
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
                            //if none of the 3 character operations add one and check for sqrt
                            tmp.append(infixExpression.charAt(i));
                            if (tmp.toString().equals("sqrt")) {
                                br.append('q');
                                break;
                            }
                            else {
                                //if it's not sqrt stop and return null
                                throw invalid;
                            }
                    }
                } else {
                    //what doesn't need change foes straight to final prepared string
                    br.append(infixExpression.charAt(i));
                }
            }
            return br.toString();
    }

    public String convertToPostfix() {
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
    private boolean needsModification(char ch) {
        return !(isDigit(ch) || isOperator(ch) || ch == '(' || ch == ')' || ch == '.');
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
