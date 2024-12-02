package kone.nassara.m1.ccsr.wok;

public class Buffet {
    private static final int MAX_QUANTITY = 1000; // Capacité maximale de chaque compartiment (1 kg)
    private final int[] quantites = {MAX_QUANTITY, MAX_QUANTITY, MAX_QUANTITY, MAX_QUANTITY}; // Poisson, viande, légumes, nouilles

    // Enumération pour identifier les compartiments
    public enum Compartiment {
        POISSON(0), VIANDE(1), LEGUMES(2), NOUILLES(3);

        private final int index;

        Compartiment(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    // Servir une portion d'un compartiment
    public synchronized boolean serve(Compartiment compartiment, int amount) {
        int index = compartiment.getIndex();
        if (quantites[index] >= amount) {
            quantites[index] -= amount;
            System.out.println("Servi " + amount + "g du compartiment " + compartiment + ". Restant : " + quantites[index] + "g.");
            return true;
        }
        System.out.println("Quantité insuffisante dans le compartiment " + compartiment + ". Restant : " + quantites[index] + "g.");
        return false;
    }

    // Réapprovisionner un compartiment
    public synchronized void refill(Compartiment compartiment) {
        int index = compartiment.getIndex();
        quantites[index] = MAX_QUANTITY;
        System.out.println("Compartiment " + compartiment + " réapprovisionné à " + MAX_QUANTITY + "g.");
    }

    // Obtenir la quantité restante dans un compartiment
    public synchronized int getQuantity(Compartiment compartiment) {
        return quantites[compartiment.getIndex()];
    }
}
