package ps.game;

import com.badlogic.gdx.utils.TimeUtils;

public class Seed extends Entity
{
    private static long LIFESPAN = 10000;

    private long m_birth;

    public Seed(int x, int y)
    {
        super(x, y);

        m_birth = TimeUtils.millis();
    }

    public boolean edible()
    {
        return TimeUtils.timeSinceMillis(m_birth) < LIFESPAN;
    }

    public boolean rotten()
    {
        return TimeUtils.timeSinceMillis(m_birth) > LIFESPAN * 2;
    }
}