package org.silo.utils;

public class Validator {
    
    public static void checkForNull(Object o, String messaje) {
        if (o == null) {
            throw new NullPointerException(messaje);
        }
    }
    
    public static void checkForContent(String text, String messaje) {
        checkForNull(text, messaje);
        if (text.isEmpty()) {
            throw new IllegalArgumentException(messaje);
        }
    }
    
    public static void checkForRange(Integer min, Integer max, Integer value, String message) {
        Validator.checkForRange(min.doubleValue(), max.doubleValue(), value.doubleValue(), message);
    }
    
    public static void checkForRange(Long min, Long max, Long value, String message) {
        Validator.checkForRange(min.doubleValue(), max.doubleValue(), value.doubleValue(), message);
    }
    
    public static void checkForRange(Float min, Float max, Float value, String message) {
        Validator.checkForRange(min.doubleValue(), max.doubleValue(), value.doubleValue(), message);
    }
    
    public static void checkForRange(double min, double max, double value, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void checkForPositive(Integer value, String message) {
        checkForPositive(value.doubleValue(), message);
    }
    
    public static void checkForPositive(Long value, String message) {
        checkForPositive(value.doubleValue(), message);
    }
    
    public static void checkForPositive(Float value, String message) {
        checkForPositive(value.doubleValue(), message);
    }
    
    public static void checkForPositive(double value, String message) {
        if (value < 1) {
            throw new IllegalArgumentException(message);
        }
    }
    
}
