package kone.nassara.m1.ccsr.wok;

public class Logger {
    public static synchronized void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
