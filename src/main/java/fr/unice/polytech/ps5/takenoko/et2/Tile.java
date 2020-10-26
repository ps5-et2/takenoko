package fr.unice.polytech.ps5.takenoko.et2;

/**
 * Base tile class.
 */
public abstract class Tile
{
    protected final Edge[] edges = new Edge[6];

    /**
     * @param num edge index
     * @return the edge corresponding to the specified number
     */
    public Edge getEdge(int num)
    {
        num %= 6;
        if (num < 0)
        {
            num += 6;
        }
        return edges[num];
    }
}
