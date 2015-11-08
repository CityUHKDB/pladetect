package edu.cityu.pladetect;

public class NoSuchFieldNameException extends Exception {
    public NoSuchFieldNameException() {
        super("No such HTML field found");
    }
}
