// Implémentations des opérations unaires
class Sin extends OperationUnaire {
    public Sin(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.sin(valeur * (Math.PI / 180));
    }
}
