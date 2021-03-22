package io.github.donut.proj.utils;

/**
 * Counter class used for tracking iterative operations
 * @author Kord Boniadi
 * @deprecated no longer needed
 */
@Deprecated public class Counter {
    private int curr;
    private int prev;
    private final int limit;
    private final int DEFAULT_LIMIT = Integer.MAX_VALUE;

    /**
     * Constructor
     * @author Kord Boniadi
     */
    public Counter() {
        this.curr = 0;
        this.prev = this.curr - 1;
        this.limit = DEFAULT_LIMIT;
    }

    /**
     * Constructor
     * @param limit non-inclusive increment limit
     * @author Kord Boniadi
     */
    public Counter(int limit) {
        this(0, limit);
    }

    /**
     * Constructor
     * @param start counter starting point
     * @param limit non-inclusive increment limit
     * @author Kord Boniadi
     */
    public Counter(int start, int limit) {
        if ((start < 0 || start >= DEFAULT_LIMIT) || (limit < 0 || limit >= DEFAULT_LIMIT))
            throw new IllegalArgumentException("an illegal argument was detected");
        this.curr = start;
        this.prev = this.curr - 1;
        this.limit = limit;
    }

    /**
     * @return copy of Counter instance
     * @author Kord Boniadi
     */
    public Counter createEquivalentCounter() {
        return new Counter(getCurrent(), limit);
    }

    /**
     * increased the curr value
     * @author Kord Boniadi
     */
    public void increment() {
        curr = ++curr % limit;
        prev = (curr + limit - 1) % limit;
    }

    /**
     * @param value counter starting point
     * @author Kord Boniadi
     */
    public void setCurr(int value) {
        if (value < 0 || value >= limit)
            throw new IllegalArgumentException("value out of range");
        this.curr = value;
        this.prev = this.curr - 1;
    }

    /**
     * @return value of counter pre-increment
     * @author Kord Boniadi
     */
    public int getPrev() {
        return prev;
    }

    /**
     * @return counter
     */
    public int getCurrent() {
        return curr;
    }

    /**
     * sets counter to 0
     * @author Kord Boniadi
     */
    public void reset() {
        setCurr(0);
    }

    /**
     * @return String representation of the counter value
     */
    @Override
    public String toString() {
        return Integer.toString(curr);
    }
}
