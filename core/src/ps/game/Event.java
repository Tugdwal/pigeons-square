package ps.game;

import ps.math.Point;

public class Event extends Entity
{
    private float m_duration;
    private Point position;

    public Event(int x, int y)
    {
        super(x, y);
        position = new Point (x, y);
    }

    public float getDuration()
    {
        return m_duration;
    }

    public void update(float delta)
    {
        m_duration += delta;
    }
    
    public Point getPosition () {
    	return position;
    }
}
