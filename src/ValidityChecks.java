import javax.print.attribute.HashPrintServiceAttributeSet;

import static java.lang.Character.isDigit;

public class ValidityChecks {
    private String pre;
    ValidityChecks(String pre){
        this.pre = pre;
    }
    boolean Valid() {
        if (pre == null || pre.isEmpty()) {
            return false;
        }
        return validParenthesis() && validInputs();
    }
    private boolean validParenthesis() {
        //counts parenthesis to check their validity
        int n = 0;
        for (int i = 0; i < pre.length(); i++) {
            if (pre.charAt(i) == '(') {
                n++;
            }else if (pre.charAt(i) == ')') {
                n--;
            }
        }
        return n == 0;
    }
//some very basic input validation to root out very obviously wrong expressions
    private boolean validInputs() {
        //checks for input validity
        for (int i = 0; i < pre.length(); i++) {
            if (!isDigit(pre.charAt(i))) {
                switch (pre.charAt(i)){
                    case 's', 'e', 'c', 'l','q':
                        if (i+1 >= pre.length()) return false;
                        if (!isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(') {
                            return false;
                        }
                        if (i-1 >= 0){
                            if(isDigit(pre.charAt(i-1))){
                            return false;
                            }
                        }
                        break;
                    case '+', '-', '*', '/':
                        if (i+1 >= pre.length() || i-1 < 0) return false;
                        if((isDigit(pre.charAt(i+1)) || pre.charAt(i+1) == '(' || Parser.isSpecial(pre.charAt(i+1)))
                                &&(isDigit(pre.charAt(i-1)) || pre.charAt(i-1) == ')')){
                            break;
                        }
                        else return false;
                    case '(':
//no need to check if i+1>= pre.length since the validParenthesis does it
                        if (pre.charAt(i+1) == ')') return false;
                        break;
                    case ')','.':
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }
//    private boolean isValid(char c) {
//        //checks if the char is a math operation/parenthesis
//        return switch (c) {
//            case '+', '-', '*', '/' , '(', ')', 's', 'e', 'c', 'l', 'q','.'-> true;
//            default -> false;
//        };
//    }
}
