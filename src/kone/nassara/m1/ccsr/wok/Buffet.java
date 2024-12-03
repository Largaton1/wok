package kone.nassara.m1.ccsr.wok;

public class Buffet {
    private static final int CAPACITY = 1000;
    private final int[] compartiments = {CAPACITY, CAPACITY, CAPACITY, CAPACITY};

    public synchronized boolean servir(int index, int quantite) {
        if (compartiments[index] >= quantite) {
            compartiments[index] -= quantite;
            return true;
        }
        return false;
    }

    public synchronized void recharger(int index) {
        compartiments[index] = CAPACITY;
    }

    public synchronized int getQuantite(int index) {
        return compartiments[index];
    }
}
