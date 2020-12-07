package fr.unice.polytech.ps5.takenoko.et2.util;

/**
 * Generic Pair class (God bless C++)
 */
public class Pair<T1, T2>
{
    public final T1 first;
    public final T2 second;

    private Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Generate a new Pair
     *
     * @param first  First element to be paired
     * @param second Second element to be paired
     * @param <T1>   Class of the first element
     * @param <T2>   Class of the second element
     * @return The pair of [first, second]
     */
    public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second)
    {
        return new Pair<>(first, second);
    }

    private static int hashCode(Object o)
    {
        return o == null ? 0 : o.hashCode();
    }

    /**
     * Get the hashcode
     *
     * @return Hashcode of the Pair (first and second)
     */
    @Override
    public int hashCode()
    {
        return 31 * hashCode(first) + hashCode(second);
    }

    /**
     * Check if the Object obj is equal to this Pair
     *
     * @param obj Object to be compared
     * @return true if obj is instance of pair and his 2 elements are equals, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Pair))
        {
            return false;
        }
        if (this == obj)
        {
            return true;
        }
        var pair = (Pair) obj;
        return equals(first, pair.first)
            && equals(second, pair.second);
    }

    private boolean equals(Object o1, Object o2)
    {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    /**
     * Convert the pair into a String
     *
     * @return The String representing the Pair
     */
    @Override
    public String toString()
    {
        return "(" + first + ", " + second + ')';
    }

    /**
     * Get first element of the Pair
     *
     * @return The first element of the Pair
     */
    public T1 getFirst()
    {
        return first;
    }

    /**
     * Get second element of the Pair
     *
     * @return The second element of the Pair
     */
    public T2 getSecond()
    {
        return second;
    }
}