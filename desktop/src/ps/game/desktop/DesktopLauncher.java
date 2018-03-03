package ps.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ps.game.PSGame;

public class DesktopLauncher
{
    public static void main (String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.resizable = false;

        new LwjglApplication(new PSGame(), config);
    }
}
