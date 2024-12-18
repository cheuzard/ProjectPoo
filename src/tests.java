public class tests {
    public static String[]  expressions = {
         "4*(2+3)",
         "sqrt(25) + log(100) * sin(45) / 2",
         "exp(log(3)) * cos(60) + sqrt(16) - 4",
         "(5 * sin(30)) / (log(10) + sqrt(9))",
         "log(exp(2)) + sqrt(81) * cos(45) - 3",
         "sin(sqrt(16)) * log(25) + exp(0.5) / 2",
         "(3 * cos(90)) + sqrt(log(100)) * 2",
         "exp(sin(45)) - log(16) + sqrt(25) / 3",
         "sqrt(log(64)) * sin(60) + cos(30) * 4",
         "(5 * exp(0.5)) / (log(8) + sin(45))",
         "log(sqrt(100)) * cos(0) + sin(sqrt(16)) * 2",
         "2.5*2",
         "2",
         "2log10",
         "5sin90",
         ".3*3",
         ".2",
         ".5*2"
    };
    public static double[] expected = {
            20,
            5.70710,
            0.80571,
            0.625,
            4.23254,
            0.92187,
            2.82842,
            2.49066,
            4.62798,
            5.11962,
            1.13951,
            5,
            2,
            2,
            5,
            0.9,
            0.2,
            1
    };
    public static String[] Etests = {
         "log",
         "log()",
         "()",
         "s",
         "lo",
         "lov",
         "sin2log",
         "*log(2)",
         "+",
         "sin2log",
         "2exp",
         "sgdsg",
         "1,23",
         "log(2,3)",
         "sin(1,5)",
         "exp(4,2)",
         "cos(3,14)",
         "*1.23",
         "/2.45",
         "+3,67",
         "-4,89",
         "log(2,)",
         "exp(,4)",
         "cos(3,)",
         "1,,23",
         "log(1,2,3)",
         "sin(4,5,6)",
         "exp(7,8,9)",
         "*,",
         "/.",
         "+*",
         "-/",
         "log()",
         "sin()",
         "exp()",
         "cos()",
         "1log",
         "2sin",
         "3cos",
         "4exp",
         "*log",
         "/sin",
         "+cos",
         "-exp",
         "log(log)",
         "sin(sin)",
         "cos(cos)",
         "exp(exp)",
         "20.",
         "log(100)3",
         "sin(100) 4",
         "(1.4.2)*2",
    };




    public static double truncateToDecimal(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.floor(value * factor) / factor;
    }

    public static void main(String[] args) {
        int errors = 0;
        int valid = 0;
        double result = 0;
        System.out.println("normal result tests:");
        for (int i = 0;i<expressions.length;i++){
            System.out.println("your expression: " + expressions[i]);
            try{
                result = truncateToDecimal(Calculatrice.calc(expressions[i]),5);
                if (result == expected[i]){
                    System.out.println("                                       valid");
                    valid++;
                }else{
                    errors++;
                }
            }catch(Exception e){
                errors++;
            }
            System.out.println("result: "+ result);
            System.out.println("expect: "+expected[i]);
            System.out.println("---------------------------------------------");
        }
        int npassed = 0;int nerrors = 0;
        for (String expr : expressions) {
            try {
                ValidityChecks.Valid(expr.toLowerCase().replaceAll(" ", ""));
                System.out.println("valid");
                npassed++;
            }catch(Exception e){
                nerrors++;
                System.out.println("---------------------------------------------");
            }
        }
        int epassed = 0;int eerrors = 0;
        for(String expr : Etests) {
            try{
                ValidityChecks.Valid(expr.toLowerCase().replaceAll(" ", ""));
                eerrors++;
                System.out.println("         failed");
            } catch(Exception e) {
                epassed++;
                System.out.println("valid");
            }
//            System.out.println("                           "+ValidityChecks.Valid(expr.toLowerCase().replaceAll(" ", "")));
            System.out.println("---------------------------------------------");
        }
        System.out.println("validations:");
        System.out.println("finished normal tests with "+ npassed+" passes, and "+ nerrors+" errors");
        System.out.println("finished error tests with "+ epassed+" passes, and "+ eerrors+" errors");
        System.out.println("calculations:");
        System.out.println("finished tests with "+ valid+" passes, and "+ errors+" errors");

    }
}