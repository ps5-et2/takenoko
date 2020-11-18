package fr.unice.polytech.ps5.takenoko.et2.board;

import java.util.Objects;

/**
 * 2D hexagonal-space vector
 */
public class TilePosition
{
    public static final TilePosition ZERO = new TilePosition(0, 0);

    /**
     * X coordinate
     */
    private final int x;
    /**
     * Y coordinate
     */
    private final int y;

    /**
     * Instanciates a new vector
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public TilePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Position comparer. To be used only when deterministic storage order is needed.
     *
     * @param a first vector
     * @param b second vector
     * @return sign of comparison
     */
    static int storageComparer(TilePosition a, TilePosition b)
    {
        Objects.requireNonNull(a, "tile position a must not be null");
        Objects.requireNonNull(a, "tile position b must not be null");
        var xc = Integer.compare(a.x, b.x);
        if (xc != 0)
        {
            return xc;
        }
        return Integer.compare(a.y, b.y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    /**
     * @param other addend
     * @return the elementwise sum of the two vectors
     */
    public TilePosition add(TilePosition other)
    {
        return new TilePosition(x + other.x, y + other.y);
    }

    /**
     * @param other subtrahend
     * @return the elementwise difference of the two vectors
     */
    public TilePosition sub(TilePosition other)
    {
        return new TilePosition(x - other.x, y - other.y);
    }

    private static int sign(int n)
    {
        return n < 0 ? -1 : 1;
    }

    /**
     * @return the basis for the specified vector, or null if the vector is a linear combination of multiples bases
     */
    public TilePosition getBasis()
    {
        if (x == 0)
            return new TilePosition(0, sign(y));
        if (y == 0)
            return new TilePosition(sign(x), 0);
        if (x == -y)
            return new TilePosition(sign(x), sign(y));

        return null;
    }

    @Override
    public boolean equals(Object other)
    {
        Objects.requireNonNull(other, "other must not be null");
        if (other instanceof TilePosition)
        {
            TilePosition pos = (TilePosition) other;
            return pos.x == x && pos.y == y;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    /**
     * @return a String of the position of the tile
     */
    @Override
    public String toString()
    {
        return "[Position (" + x + ", " + y + ")]";
    }
}
