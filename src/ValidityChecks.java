
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
        System.out.println(pre);
        if (pre == null || pre.isEmpty()){
            return false;
        }
        int FirstChar;
        //checks for input validity
        for (int i = 0; i < pre.length(); i++) {
            if (!isDigit(pre.charAt(i))) {
                switch (pre.charAt(i)){
                    case 's','e', 'c', 'l':
                        if (i+3 > pre.length()) return false;
                        StringBuilder br =  new StringBuilder();
                        FirstChar = i;
                        for (int j = 0; j < 3; j++) {
                            br.append(pre.charAt(i));
                            i++;
                        }
                        i--;
                        switch (br.toString()){
                            case "sin":
                            case "cos":
                            case "exp":
                            case "log":
                                break;
                            default:
                                if(i+1 >= pre.length()) return false;
                                i++;
                                br.append(pre.charAt(i));
                                if (br.toString().equals("sqrt")) break;
                                else return false;
                        }
                        //if the next char is out of bounds return false
                        //because these operations have their operand right after them
                        if (i+1 >= pre.length()) return false;
                        //check that the next char is either a digit or an opening parentheses
                        if (!isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(') {
                            return false;
                        }
                        //if the previous char is a digit return false
                        // expressions like 2sin(4) arnt allowed, require explicit *
//                        if (FirstChar-1 >= 0){
//                            if(isDigit(pre.charAt(FirstChar-1))){
//                            return false;
//                            }
//                        }
                        break;
                    case '+', '-', '*', '/':
                        //check if the previous and next char are in bounds
                        //because expressions like +, / need two operand one before and after them
                        if (i+1 >= pre.length() || i-1 < 0) return false;
                        //before can be either a digit or closing parentheses.
                        //next can be digit, opening parentheses, or a special operation (sin,cos...)
                        if ((!isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(' && !Parser.isSpecial(pre.charAt(i + 1)))
                                || (!isDigit(pre.charAt(i - 1)) && pre.charAt(i - 1) != ')')) {
                            return false;
                        }
                        break;
                    case '(':
//no need to check if i+1>= pre.length since the validParenthesis does it
                        if (pre.charAt(i+1) == ')') return false;
                        break;
                    case'.':
                        if (i+1 >= pre.length() || i-1 < 0) return false;
                        if (!isDigit(pre.charAt(i + 1)) || !isDigit(pre.charAt(i - 1)))return false;
                        break;
                    case ')':
                        if (i+1 >= pre.length() && isDigit(pre.charAt(i+1)))return false;
                    case ' ':
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }
}
