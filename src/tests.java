import static java.lang.Character.isDigit;

public class tests {
    public static void main(String[] args) {
        Transformation tr = new Transformation("30 +4/ (Sqrt2+3*LoG4)");
        System.out.println(tr.transform());
    }
}
