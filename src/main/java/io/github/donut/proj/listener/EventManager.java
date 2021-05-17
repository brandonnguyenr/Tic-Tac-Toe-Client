package io.github.donut.proj.listener;

import io.github.donut.proj.utils.DataValidation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all Subjects and there subscribed Observers
 * @see IObserver
 * @see ISubject
 * @author Kord Boniadi
 */
public final class EventManager {
    private static final ConcurrentHashMap<ISubject, Set<IObserver>> eventMap = new ConcurrentHashMap<>();
    private EventManager() {
        /*
         *  Don't remove...
         *  this prevents java from generating a default constructor
         */
    }

    /**
     * Creates an association with a Subject and an Observer allowing for
     * the transfer of data
     * @param subj Subject class
     * @param obs Observer class
     * @author Kord Boniadi
     */
    public static void register(ISubject subj, IObserver obs) {
        DataValidation.ensureObjectNotNull("ISubject", subj);
        DataValidation.ensureObjectNotNull("IObserver", obs);

        eventMap.computeIfAbsent(subj, k -> new HashSet<>()).add(obs);
    }

    /**
     * Removes an association between a Subject and an Observer preventing
     * further data sharing
     * @param subj Subject class
     * @param obs Observer class
     * @author Kord Boniadi
     */
    public static void unregister(ISubject subj, IObserver obs) {
        DataValidation.ensureObjectNotNull("ISubject", subj);
        DataValidation.ensureObjectNotNull("IObserver", obs);

        Set<IObserver> observersReference = eventMap.get(subj);
        if (observersReference != null) {
            observersReference.remove(obs);

            if (observersReference.isEmpty())
                eventMap.remove(subj);
        }

    }

    /**
     * Sends data packaged in an Object to associated Observers
     * @param subj Subject class
     * @param obj Observer class
     * @author Kord Boniadi
     */
    public static void notify(ISubject subj, Object obj) {
        DataValidation.ensureObjectNotNull("ISubject", subj);

        Set<IObserver> observersReference = eventMap.get(subj);
        if (observersReference != null)
            observersReference.forEach(k -> k.update(obj));
    }

    /**
     * Removes all associated Observers from Subject
     * @param subj Subject class
     * @author Kord Boniadi
     */
    public static void removeAllObserver(ISubject subj) {
        DataValidation.ensureObjectNotNull("ISubject", subj);
        eventMap.remove(subj);
    }

    /**
     * Checks for any associated Observers
     * @param subj Subject class
     * @return Boolean
     * @author Kord Boniadi
     */
    public static boolean hasObservers(ISubject subj) {
        DataValidation.ensureObjectNotNull("ISubject", subj);
        return eventMap.get(subj) != null;
    }

    public static void cleanup() {
        eventMap.clear();
    }
}
