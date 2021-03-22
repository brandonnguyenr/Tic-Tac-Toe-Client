package io.github.donut.proj.listener;

/**
 * Adds ability to get information from Subjects
 * @see ISubject
 * @author Kord Boniadi
 */
public interface IObserver {

    /**
     * New info is received through this method. Object decoding is needed
     * @param eventType General Object type
     * @author Kord Boniadi
     */
    void update(Object eventType);
}
