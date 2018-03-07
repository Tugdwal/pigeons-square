package ps.game;

public class Event extends Entity
{
    private float m_duration;

    public Event(int x, int y)
    {
        super(x, y);
    }

    public float getDuration()
    {
        return m_duration;
    }

    public void update(float delta)
    {
        m_duration += delta;
    }
}
