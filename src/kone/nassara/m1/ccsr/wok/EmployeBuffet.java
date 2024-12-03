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
            while (true) {
                for (Compartiment compartiment : Compartiment.values()) {
                    if (buffet.getQuantite(compartiment) < 100) {
                        buffet.recharger(compartiment);
                    }
                }
                Thread.sleep(delai); // Pause configurable entre les v�rifications
            }
        } catch (InterruptedException e) {
            Logger.log("Employ� interrompu.");
        }
    }
}
