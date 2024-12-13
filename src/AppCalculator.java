import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AppCalculator {
    public static void main(String[] args) throws IOException {
        String keepGoing;
        do{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter you mathematical expression: ");
            System.out.println("result: "+Calculator.calc(reader.readLine()));
            BufferedReader expression = new BufferedReader(new InputStreamReader(System.in));
            do {
            System.out.print("do you wish to continue? (Y/N): ");
            keepGoing = reader.readLine().trim().toLowerCase();
            }while(!(keepGoing.equals("y") || keepGoing.equals("n")));
        }while(keepGoing.equals("y"));
    }
}
