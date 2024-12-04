package kone.nassara.m1.ccsr.wok;

public class EmployeBuffet implements Runnable {
    private final Buffet buffet;
    private final int delai;

    public EmployeBuffet(Buffet buffet, int delai) {
        this.buffet = buffet;
        this.delai = delai;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) { // Vérification explicite de l'interruption
                for (Compartiment compartiment : Compartiment.values()) {
                    if (buffet.getQuantite(compartiment) < 100) {
                        buffet.recharger(compartiment);
                    }
                }
                Thread.sleep(delai); // Pause configurable entre les vérifications
            }
        } catch (InterruptedException e) {
            // Gestion explicite de l'interruption
            Logger.log("Employé interrompu.");
            Thread.currentThread().interrupt(); // Restaure l'état d'interruption
        }
    }
}
