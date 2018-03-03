package ps.game;

import ps.math.Point;

public class Entity
{
    protected Point m_position;

    public Entity(int x, int y)
    {
        m_position = new Point(x, y);
    }

    public Point getPosition()
    {
        return m_position;
    }

    public int getX()
    {
        return m_position.getX();
    }

    public int getY()
    {
        return m_position.getY();
    }
}