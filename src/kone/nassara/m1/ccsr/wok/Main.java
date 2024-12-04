package kone.nassara.m1.ccsr.wok;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Démarre la simulation avec 40 clients
        restaurant.startSimulation(40);

        // Attendre que tous les clients et le cuisinier finissent
        restaurant.attendreFinSimulation();
    }
}
