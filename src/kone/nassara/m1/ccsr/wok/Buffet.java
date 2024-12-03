package kone.nassara.m1.ccsr.wok;

public class Buffet {
    private final int[] quantites = {
        Compartiment.CAPACITY,
        Compartiment.CAPACITY,
        Compartiment.CAPACITY,
        Compartiment.CAPACITY
    };
    private int nombreReapprovisionnements = 0;

    // M�thode pour afficher un compartiment sous forme de barre de progression
    private String afficherBarreProgression(int quantite) {
        int pourcentage = (quantite * 100) / Compartiment.CAPACITY;
        return String.format("[%-100s] %d%%", "=".repeat(pourcentage), pourcentage);
    }

    // Servir une portion d'un compartiment
    public synchronized boolean servir(Compartiment compartiment, int quantite) {
        int index = compartiment.ordinal();
        if (quantites[index] >= quantite) {
            quantites[index] -= quantite;
            Logger.log(compartiment + ": " + afficherBarreProgression(quantites[index]));
            return true;
        }
        Logger.log(compartiment + ": Quantit� insuffisante. Attente...");
        return false;
    }

    // R�approvisionner un compartiment
    public synchronized void recharger(Compartiment compartiment) {
        int index = compartiment.ordinal();
        quantites[index] = Compartiment.CAPACITY;
        nombreReapprovisionnements++;
        Logger.log(compartiment + ": " + afficherBarreProgression(quantites[index]));
    }

    // Obtenir la quantit� restante dans un compartiment
    public int getQuantite(Compartiment compartiment) {
        return quantites[compartiment.ordinal()];
    }

    public synchronized int getNombreReapprovisionnements() {
        return nombreReapprovisionnements;
    }
}
