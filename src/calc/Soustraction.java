package calc;

class Soustraction extends OperationBinaire {
    public Soustraction(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        return valeur1 - valeur2;
    }
}
