package io.github.donut.proj.utils;

/**
 * Automates Validation for Strings and Objects
 * @author Kord Boniadi
 */
public final class DataValidation {
    /**
     * Constructor
     * @author Kord Boniadi
     */
    private DataValidation() {}

    /**
     * @param propertyName used for error output
     * @param value String to check
     * @throws NullPointerException string is null
     * @author Kord Boniadi
     */
    public static void ensureNonEmptyString(String propertyName, String value) throws NullPointerException {
        if (value == null || value.trim().equals(""))
            throw new NullPointerException(String.format("%s cannot be null or empty", propertyName));
    }

    /**
     * @param propertyName used for error output
     * @param obj Object to check
     * @throws NullPointerException object is null
     * @author Kord Boniadi
     */
    public static void ensureObjectNotNull(String propertyName, Object obj) throws NullPointerException {
        if (obj == null)
            throw new NullPointerException(String.format("%s cannot be null", propertyName));
    }
}