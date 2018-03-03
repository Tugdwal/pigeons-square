package ps.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PSGame extends ApplicationAdapter
{
    private final static int DOVES = 4;

    private Square m_square;
    private ArrayList<Dove> m_doves;

    private Texture m_texture_seed;
    private Texture m_texture_dove;

    private TextureRegion m_texture_region_dove;

    SpriteBatch batch;

    @Override
    public void create()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                m_square.feed(screenX, Gdx.graphics.getHeight() - screenY);
                return true;
            }
        });

        m_square = new Square();
        m_doves = new ArrayList<>();

        for (int i = 0; i < DOVES; i++) {
            m_doves.add(
                    Dove.newDove(
                    ThreadLocalRandom.current().nextInt(0, Dove.Type.values().length),
                    ThreadLocalRandom.current().nextInt(0, Gdx.graphics.getWidth()),
                    ThreadLocalRandom.current().nextInt(0, Gdx.graphics.getHeight()),
                    ThreadLocalRandom.current().nextInt(1, 10),
                    m_square
                    ));
            new Thread(m_doves.get(i), String.valueOf(i)).start();
        }

        m_texture_seed = new Texture(Gdx.files.internal("seed.png"));
        m_texture_dove = new Texture(Gdx.files.internal("dove.png"));

        m_texture_region_dove = new TextureRegion(m_texture_dove, 2, 21, 32, 32);

        batch = new SpriteBatch();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 1, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        synchronized (m_square) {
            m_square.getSeeds().removeIf(seed -> seed.rotten());

            for (Seed seed : m_square.getSeeds()) {
                if (!seed.edible()) {
                    batch.setColor(1, 0, 0, 0.5f);
                }

                batch.draw(m_texture_seed, seed.getX() - m_texture_seed.getWidth() / 2 + 1, seed.getY() - m_texture_seed.getHeight() / 2 + 1);

                batch.setColor(Color.WHITE);
            }
        }

        for (Dove dove : m_doves) {
            batch.draw(m_texture_region_dove, dove.getX() - m_texture_region_dove.getRegionWidth() / 2 + 1, dove.getY() - m_texture_region_dove.getRegionHeight() / 2 + 1);
        }

        batch.end();
    }

    @Override
    public void dispose()
    {
        for (int i = 0; i < DOVES; i++) {
            m_doves.get(i).exit();
        }

        m_texture_seed.dispose();
        m_texture_dove.dispose();

        batch.dispose();
    }
}
