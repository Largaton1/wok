package kone.nassara.m1.ccsr.wok;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        restaurant.startSimulation(40); // Simuler avec 40 clients

        // Génération d'un rapport final
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.log("\n=== RAPPORT FINAL ===");
            Logger.log("Nombre total de clients : " + restaurant.getTotalClients());
            Logger.log("Nombre total de réapprovisionnements : " + restaurant.getBuffet().getNombreReapprovisionnements());
            Logger.log("Nombre total de plats cuits : " + restaurant.getTotalPlatsCuits());
            Logger.log("Temps d'attente total au buffet : " + restaurant.getTotalTempsAttenteBuffet() + " ms");
            Logger.log("Temps d'attente moyen au buffet : " + 
                (restaurant.getTotalTempsAttenteBuffet() / (double) restaurant.getTotalClients()) + " ms");
        }));
    }
}
