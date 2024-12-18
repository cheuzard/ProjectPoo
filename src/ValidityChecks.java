
import static java.lang.Character.isDigit;

public class ValidityChecks {

    static void Valid(String pre) {
        validParenthesis(pre);
        validInputs(pre);
    }
    private static void validParenthesis(String pre) {
        //counts parenthesis to check their validity
        int n = 0;
        for (int i = 0; i < pre.length() && n>=0; i++) {
            if (pre.charAt(i) == '(') {
                n++;
            }else if (pre.charAt(i) == ')') {
                n--;
            }
        }
        if (n!=0) {
            throw new IllegalArgumentException("Invalid parenthesis");
        }
    }
    //some very basic input validation to root out very obviously wrong expressions
    private static void validInputs(String pre) {
        if (pre == null || pre.isEmpty()){
            throw new IllegalArgumentException("no inputs");
        }
        int FirstChar;
        //checks for input validity
        for (int i = 0; i < pre.length(); i++) {
            if (isDigit(pre.charAt(i))) {
                //this checks the validity of floating point numbers, to check for invalid expressions
                //such as 1.2.3 if there are multiple points
                int dots = 0;
                while(i+1 < pre.length() && ( pre.charAt(i+1) == '.' || isDigit(pre.charAt(i+1)) ) ){
                    i++;
                    if(pre.charAt(i) == '.'){
                        if (i + 1 >= pre.length() || !isDigit(pre.charAt(i + 1)) || !isDigit(pre.charAt(i - 1))){
                            throw new IllegalArgumentException("invalid floating point number");
                        }
                        dots++;
                        if (dots > 1) throw new IllegalArgumentException("invalid floating point number");;
                    }
                }
            } else {
                switch (pre.charAt(i)){
                    case 's','e', 'c', 'l':
                        if (i+3 > pre.length()) throw new IllegalArgumentException("invalid mathematical expression");

                        StringBuilder br =  new StringBuilder();

                        //for later checks we need to keep track of the location of the first character
                        FirstChar = i;
                        //add the first three characters to a string
                        //three because sin, cos… share the same length 3
                        br.append(pre.charAt(i++)).append(pre.charAt(i++)).append(pre.charAt(i));
                        switch (br.toString()){
                            case "log":
                            case "sin":
                            case "cos":
                            case "exp":
                                break;
                            default:
                                if(i+1 >= pre.length()) throw new IllegalArgumentException("invalid mathematical expression");
                                //add one char to the string then check again for sqrt
                                i++;
                                br.append(pre.charAt(i));
                                if (br.toString().equals("sqrt")) break;
                                else throw new IllegalArgumentException("invalid mathematical expression");
                        }
                        //if the next char is out of bounds throw an exception
                        //because these operations have their operand right after them
                        //check that the next char is either a digit or an opening parentheses
                        if (i + 1 >= pre.length() || !isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(' && pre.charAt(i + 1) != '-'){
                            throw new IllegalArgumentException("invalid mathematical expression");
                        }
                        break;
                    case '-':
                        if (i+1 >= pre.length()) throw new IllegalArgumentException("invalid '" + pre.charAt(i)+"' operation");
                        if(!isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(' && !Parser.isSpecial(pre.charAt(i + 1))
                        && pre.charAt(i + 1) != '-'){
                            throw new IllegalArgumentException("invalid '" + pre.charAt(i)+"' operation");
                        }
                        break;
                    case '+', '*', '/':
                        //check if the previous and next char are in bounds
                        //because expressions like +, / need two operand one before and after them
                        if (i+1 >= pre.length() || i-1 < 0) throw new IllegalArgumentException("invalid '" + pre.charAt(i)+"' operation");
                        //before can be either a digit or closing parentheses.
                        //next can be digit, opening parentheses, or a special operation (sin,cos...)
                        if ((!isDigit(pre.charAt(i + 1)) && pre.charAt(i + 1) != '(' && !Parser.isSpecial(pre.charAt(i + 1))
                                && pre.charAt(i + 1) != '-')
                                || (!isDigit(pre.charAt(i - 1)) && pre.charAt(i - 1) != ')')) {
                            throw new IllegalArgumentException("invalid '" + pre.charAt(i)+"' operation");
                        }
                        break;
                    case '(':
                        //no need to check if i+1>= pre.length since the validParenthesis does it
                        if (pre.charAt(i+1) == ')') throw new IllegalArgumentException("Invalid parenthesis");
                        break;
                    case'.':
                        if (i + 1 >= pre.length() || !isDigit(pre.charAt(i + 1))) throw new IllegalArgumentException("Invalid parenthesis");
                        break;
                    case ')':
                        if (i+1 < pre.length() && isDigit(pre.charAt(i+1))) throw new IllegalArgumentException("Invalid parenthesis");
                    case ' ':
                        break;
                    default:
                        throw new IllegalArgumentException("invalid mathematical expression");
                }
            }
        }
    }
}
