import java.lang.reflect.Array;

public class tests {
 public static String[]  expressions = {
//         "(5 * exp(0.5)) / (log(8) + sin(45))",
         "sqrt(25) + log(100) * sin(45) / 2",                 // Mixed functions with division
         "exp(log(3)) * cos(60) + sqrt(16) - 4",              // Exponential, trigonometric, and arithmetic
         "(5 * sin(30)) / (log(10) + sqrt(9))",               // More complex fraction with trigonometry
         "log(exp(2)) + sqrt(81) * cos(45) - 3",              // Nested logarithm and exponential
         "sin(sqrt(16)) * log(25) + exp(0.5) / 2",          // Complex trigonometric and exponential
         "(3 * cos(90)) + sqrt(log(100)) * 2",                // Trigonometric with logarithmic square root
         "exp(sin(45)) - log(16) + sqrt(25) / 3",             // Exponential with trigonometric function
         "sqrt(log(64)) * sin(60) + cos(30) * 4",             // Nested square root and logarithm
         "(5 * exp(0.5)) / (log(8) + sin(45))",               // Complex fraction with exponential
         "log(sqrt(100)) * cos(0) + sin(sqrt(16)) * 2"        // Mixed logarithmic and trigonometric
 };
 public static String[] Etests = {
         "log",
         "log()",
         "()",
         "s",
         "lo",
         "lov",

         "sin2log",
         "2exp",
         "sgdsg",
 };

 public static double[] expected = {
         5.70710,
         0.80571,
         0.625,
         4.23254,
         0.92187,
         2.82842,
         2.49066,
         4.62798,
         5.11962,
         1.13951
    };
    // Main method to test the conversion
    public static void main(String[] args) {

        for (int i = 0;i<expressions.length;i++){
            if (truncateToDecimal(Calculator.calc(expressions[i]),5) == expected[i]){
                System.out.println("valid");
            }else{
                System.out.println("your expression: " + expressions[i]);
                System.out.println("result: "+ truncateToDecimal(Calculator.calc(expressions[i]),5));
            }
            System.out.println("---------------------------------------------");
        }
    }
    public static double truncateToDecimal(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.floor(value * factor) / factor;
    }
}