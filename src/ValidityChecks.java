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
            if (!(isDigit(pre.charAt(i)) || isValid(pre.charAt(i)))) {
                switch (br.toString()){
                    case "s", "e", "c", "l","q":
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }
    private boolean isValid(char c) {
        //checks if the char is a math operation/parenthesis
        return switch (c) {
            case '+', '-', '*', '/' , '(', ')', 's', 'e', 'c', 'l', 'q','.'-> true;
            default -> false;
        };
    }
}
