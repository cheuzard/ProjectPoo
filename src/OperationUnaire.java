// Classe abstraite pour les opérations unaires
abstract class OperationUnaire implements CalculMath {
    protected double valeur;

    public OperationUnaire(double valeur) {
        this.valeur = valeur;
    }
}
