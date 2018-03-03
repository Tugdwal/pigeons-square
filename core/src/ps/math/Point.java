package ps.math;

public class Point
{
    private int m_x;
    private int m_y;

    public Point()
    {
        this(0, 0);
    }

    public Point(int x, int y)
    {
        m_x = x;
        m_y = y;
    }

    public int getX()
    {
        return m_x;
    }

    public void setX(int x)
    {
        m_x = x;
    }

    public void addX(int x)
    {
        m_x += x;
    }

    public int getY()
    {
        return m_y;
    }

    public void setY(int y)
    {
        m_y = y;
    }

    public void addY(int y)
    {
        m_y += y;
    }

    // Norme infinie, pas besoin de racine carrée
    public int dist(Point p)
    {
        return Math.max(Math.abs(m_x - p.m_x), Math.abs(m_y - p.m_y));
    }

    @Override
    public String toString()
    {
        return "(" + m_x + ", " + m_y + ")";
    }
}
