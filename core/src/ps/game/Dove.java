package ps.game;

import ps.math.Point;

public class Dove extends Entity implements Runnable
{
    public enum Type
    {
        Ramier,
        Colombin,
        Biset
    }

    private int m_size;
    private int m_speed;
    private Square m_square;

    private boolean m_running;

    {
        m_running = false;
    }

    public Dove(int x, int y, int speed, int size, Square square)
    {
        super(x, y);
        m_size = size;
        m_speed = speed;
        m_square = square;
    }

    public String getName()
    {
        return "Dove : " + Thread.currentThread().getName();
    }

    public void move(Point target)
    {
        m_position.addX(delta(m_position.getX(), target.getX()));
        m_position.addY(delta(m_position.getY(), target.getY()));

        System.out.println(getName() + " - Target : " + target + " - Moving to " + m_position);
    }

    @Override
    public void run() {
        m_running = true;

        System.out.println(getName() + " - Coming");

        while (m_running) {
            Seed food = m_square.find(this);
            if (food != null) {
                if (food.getPosition().dist(m_position) <= m_size) {
                    if (food.edible() && m_square.eat(food)) {
                        System.out.println(getName() + " - Eat seed at " + food.getPosition().toString());
                    }
                } else {
                    move(food.getPosition());
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(getName() + " - Leaving");
    }

    public void exit()
    {
        m_running = false;
    }

    private int delta(int value, int target)
    {
        int d = target - value;
        int s = Integer.signum(d);
        int v = Integer.signum(d/m_speed) * s;

        return m_speed * s * v + d * (1 - v);
    }

    // Factory
    public static Dove newDove(int type, int x, int y, int speed, Square square)
    {
        switch (Type.values()[type]) {
        case Ramier:
            return new Dove(x, y, speed, 2, square);
        case Colombin:
            return new Dove(x, y, speed, 3, square);
        case Biset:
            return new Dove(x, y, speed, 4, square);
        default:
            return new Dove(x, y, speed, 1, square);
        }
    }
}
