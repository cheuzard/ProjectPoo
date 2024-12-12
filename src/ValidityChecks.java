import static java.lang.Character.isDigit;

public class ValidityChecks {
    private String pre;
    ValidityChecks(String pre){
        this.pre = pre;
    }
    boolean Valid() {
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

    private boolean validInputs() {
        //checks for input validity
        StringBuilder br = new StringBuilder();
        for (int i = 0; i < pre.length(); i++) {
            if (!(isDigit(pre.charAt(i)) || isOperation(pre.charAt(i)))) {
                for (int j = 0; j < 3; j++) {
                    br.append(pre.charAt(i));
                    i++;
                }
                switch (br.toString()){
                    case "sin", "exp", "cos", "log": i--; break;
                    default:
                        br.append(pre.charAt(i));
                        if (br.toString().equals("sqrt")){
                            br.delete(0, br.length());
                            break;
                        }else {
                            return false;
                        }
                }
            }
        }
        return true;
    }
    boolean isOperation(char c) {
        //checks if the char is a math operation/parenthesis
        return switch (c) {
            case '+', '-', '*', '/' , '(', ')'-> true;
            default -> false;
        };
    }
}
