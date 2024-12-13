import javax.print.attribute.HashPrintServiceAttributeSet;

import static java.lang.Character.isDigit;

public class ValidityChecks {

    static boolean Valid(String pre) {
        return validParenthesis(pre) && validInputs(pre);
    }
    private static boolean validParenthesis(String pre) {
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
    private static boolean validInputs(String pre) {
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
}
