import java.util.Stack;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Classe principale de test
public class Calculatrice {
    //Binary calculation
    static double calculate(double a, double b,char op) {
        CalculMath operation = switch (op) {
            case '+' -> new Addition(a, b);
            case '-' -> new Soustraction(a, b);
            case '*' -> new Multiplication(a, b);
            case '/' -> new Division(a, b);
            default -> null;
        };
        assert operation != null;

        return operation.Calculer();
    }
    //Unitary calculation
    static double calculate(double a,char op) {
        CalculMath operation = switch (op) {
            case 's' -> new Sin(a);
            case 'c' -> new Cos(a);
            case 'l' -> new Log(a);
            case 'e' -> new Exp(a);
            case 'q' -> new Sqrt(a);
            default -> null;
        };
        assert operation != null;
        return operation.Calculer();
    }

    static double calc(String expression) throws Exception {
//        try {
            Parser parser = new Parser(expression);
            String post = parser.convertToPostfix();

            double a = 0, b = 0;
            Stack<Double> stack = new Stack<>();
            //go through the string characters
            for (int i = 0; i < post.length(); i++) {
//                numbers were put in parentheses by the pre formater function
//                when an opening parentheses is found add the number in it to the stack
                if (post.charAt(i) == '(') {
                    StringBuilder br = new StringBuilder();
                    //go through the chars to build the number in string
                    while (post.charAt(i + 1) != ')') {
                        i++;
                        br.append(post.charAt(i));
                    }

//                  try parsing the string to double and finally add it to stack
                    stack.push(Double.parseDouble(br.toString()));
                }
                else if (Parser.isOperator(post.charAt(i))) {
                    //when a Binary operation like  '+', '-', '*', '/'
                    // is found pop 2 numbers from the stack
                        b = stack.pop();
                        a = stack.pop();

                    //perform the calculation then push it back to the stack

                    stack.push(calculate(a, b, post.charAt(i)));
                }
                else if (Parser.isSpecial(post.charAt(i))) {
                    //when a unitary operation like 's' sin, 'e' exp, 'c' cos, 'l' log, 'q' sqrt
                    //pop a number perform calculation then push result to stack
                    stack.push(calculate(stack.pop(), post.charAt(i)));
                }
            }
            return BigDecimal.valueOf(stack.pop()).setScale(14, RoundingMode.HALF_UP).doubleValue();
//        }catch (Exception e) {
//            throw invalid;
//        }
    }
}
