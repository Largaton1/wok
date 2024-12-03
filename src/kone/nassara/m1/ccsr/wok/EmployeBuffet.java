package kone.nassara.m1.ccsr.wok;

public class EmployeBuffet implements Runnable {
    private final Buffet buffet;

    public EmployeBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < 4; i++) {
                    synchronized (buffet) {
                        if (buffet.getQuantite(i) < 100) {
                            System.out.println("Employé recharge le compartiment " + i);
                            buffet.recharger(i);
                        }
                    }
                }
                Thread.sleep(200); // Pause entre les vérifications
            }
        } catch (InterruptedException e) {
            System.out.println("Employé interrompu.");
        }
    }
}
