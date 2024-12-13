import java.util.Stack;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Interface de base pour les calculs
interface CalculMath {
    double Calculer();
}

// Classe abstraite pour les opérations binaires
abstract class OperationBinaire implements CalculMath {
    protected double valeur1;
    protected double valeur2;

    public OperationBinaire(double valeur1, double valeur2) {
        this.valeur1 = valeur1;
        this.valeur2 = valeur2;
    }
}

// Classe abstraite pour les opérations unaires
abstract class OperationUnaire implements CalculMath {
    protected double valeur;

    public OperationUnaire(double valeur) {
        this.valeur = valeur;
    }
}

// Implémentations des opérations binaires
class Addition extends OperationBinaire {
    public Addition(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        return valeur1 + valeur2;
    }
}

class Soustraction extends OperationBinaire {
    public Soustraction(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        return valeur1 - valeur2;
    }
}

class Multiplication extends OperationBinaire {
    public Multiplication(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        return valeur1 * valeur2;
    }
}

class Division extends OperationBinaire {
    public Division(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        if (valeur2 == 0) {
            throw new ArithmeticException("Division par zéro impossible");
        }
        return valeur1 / valeur2;
    }
}

// Implémentations des opérations unaires
class Sin extends OperationUnaire {
    public Sin(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.sin(valeur*(Math.PI/180));
    }
}

class Cos extends OperationUnaire {
    public Cos(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.cos(valeur*(Math.PI/180));
    }
}

class Log extends OperationUnaire {
    public Log(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        //to get log base 10
        return Math.log(valeur) / Math.log(10);
    }
}

class Exp extends OperationUnaire {
    public Exp(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.exp(valeur);
    }
}

class Sqrt extends OperationUnaire {
    public Sqrt(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.sqrt(valeur);
    }
}

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
        Exception invalid = new Exception("expression is invalid");
        try {
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
                    stack.push(calculate(a ,b, post.charAt(i)));
                }
                else if (Parser.isSpecial(post.charAt(i))) {
                    //when a unitary operation like 's' sin, 'e' exp, 'c' cos, 'l' log, 'q' sqrt
                    //pop a number perform calculation then push result to stack
                    stack.push(calculate(stack.pop(), post.charAt(i)));
                }
            }
            return BigDecimal.valueOf(stack.pop()).setScale(14, RoundingMode.HALF_UP).doubleValue();
        }catch (Exception e) {
            throw invalid;
        }
    }
}
