package kone.nassara.m1.ccsr.wok;

/**
 * Représente les fonctionnalités principales d'un restaurant simulé.
 */
public interface Restaurant {

    /**
     * Un client entre dans le restaurant. Si le restaurant est plein, le client attend qu'une place se libère.
     *
     * @param client Le client qui tente d'entrer.
     */
    void entrer(Client client);

    /**
     * Un client quitte le restaurant, libérant une place.
     *
     * @param client Le client qui quitte le restaurant.
     */
    void sortir(Client client);

    /**
     * Un client accède au buffet pour se servir.
     * Cette méthode garantit la sécurité des threads pendant l'accès et gère l'attente
     * si un compartiment est vide.
     *
     * @param client Le client qui se sert au buffet.
     */
    void servirAuBuffet(Client client);

    /**
     * Surveille et réapprovisionne les compartiments du buffet si nécessaire.
     * Cette méthode est appelée par l'employé chargé du buffet.
     */
    void surveillerEtReapprovisionnerBuffet();

    /**
     * Un client s'inscrit dans la file d'attente pour la cuisson et attend son tour.
     *
     * @param client Le client en attente de cuisson.
     */
    void attendreCuisson(Client client);

    /**
     * Le cuisinier prépare le plat du prochain client dans la file d'attente.
     * Cette méthode est appelée par le thread du cuisinier.
     */
    void cuireProchainClient();

    /**
     * Renvoie l'état actuel du restaurant, comme le nombre de places occupées.
     *
     * @return Une chaîne de caractères ou une structure résumant l'état du restaurant.
     */
    String etatRestaurant();

    /**
     * Renvoie l'état actuel du buffet, y compris les quantités restantes dans chaque compartiment.
     *
     * @return Une chaîne de caractères ou une structure résumant l'état du buffet.
     */
    String etatBuffet();
}
