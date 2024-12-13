import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppCalculator {
    public static void main(String[] args) throws IOException {
        String keepGoing;
        double result;
        do{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter you mathematical expression: ");
            try{
            result = Calculatrice.calc(reader.readLine());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            BufferedReader expression = new BufferedReader(new InputStreamReader(System.in));
            do {
            System.out.print("do you wish to continue? (Y/N): ");
            keepGoing = reader.readLine().trim().toLowerCase();
            }while(!(keepGoing.equals("y") || keepGoing.equals("n")));
        }while(keepGoing.equals("y"));
    }
}
