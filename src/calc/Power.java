package calc;

public class Power extends OperationBinaire{
    public Power(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        return Math.pow(valeur1, valeur2);
    }
}
