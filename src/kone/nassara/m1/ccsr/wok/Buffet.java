package kone.nassara.m1.ccsr.wok;

public class Buffet {
    private final int[] quantites = {1000, 1000, 1000, 1000}; // Poisson, viande, légumes, nouilles (en grammes)

    // Servir une portion d'un compartiment
    public synchronized boolean serve(int compartmentIndex, int amount) {
        if (quantites[compartmentIndex] >= amount) {
            quantites[compartmentIndex] -= amount;
            return true;
        }
        return false;
    }

    // Réapprovisionner un compartiment
    public synchronized void refill(int compartmentIndex) {
        quantites[compartmentIndex] = 1000;
    }

    // Obtenir la quantité restante dans un compartiment
    public synchronized int getQuantity(int compartmentIndex) {
        return quantites[compartmentIndex];
    }
}
