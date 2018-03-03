package ps.game;

import java.util.ArrayList;

public class Square
{
    private ArrayList<Seed> m_seeds;

    public Square()
    {
        m_seeds = new ArrayList<>();
    }

    public ArrayList<Seed> getSeeds()
    {
        return m_seeds;
    }

    public synchronized void feed(int x, int y)
    {
        m_seeds.add(new Seed(x, y));
    }

    public synchronized Seed find(Dove dove)
    {
        int min = Integer.MAX_VALUE;
        Seed s = null;

        for (Seed seed : m_seeds) {
            int d = dove.getPosition().dist(seed.getPosition());
            if (d < min) {
                min = d;
                s = seed;
            }
        }

        return s;
    }

    public synchronized boolean eat(Seed s)
    {
        return m_seeds.remove(s);
    }
}
