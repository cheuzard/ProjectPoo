package calc;

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
