package ps.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PSGame extends ApplicationAdapter
{
    private final static int DOVES = 4;
    private static final int FRAME_COLS = 5, FRAME_ROWS = 5;

    private Square m_square;
    private ArrayList<Dove> m_doves;
    private Event m_explosion;

    private Texture m_texture_seed;
    private Texture m_texture_dove;
    private Texture m_texture_explosion;

    private TextureRegion m_texture_region_dove;

    private Animation<TextureRegion> m_animation_explosion;

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
        m_texture_explosion =  new Texture(Gdx.files.internal("explosion.png"));

        int x = 0;
        int y = 6;
        int dx = 32;
        int dy = 32;
        m_texture_region_dove = new TextureRegion(m_texture_dove, 2 + x * (dx + 2), 21 + y * (dy + 21), dx, dx);

        batch = new SpriteBatch();

        TextureRegion[][] tmp = TextureRegion.split(m_texture_explosion, m_texture_explosion.getWidth() / FRAME_COLS, m_texture_explosion.getHeight() / FRAME_ROWS);
        TextureRegion[] explosion_frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosion_frames[index++] = tmp[i][j];
            }
        }

        m_animation_explosion = new Animation<TextureRegion>(0.025f, explosion_frames);
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

        if (m_explosion != null) {
            TextureRegion texture_explosion = m_animation_explosion.getKeyFrame(m_explosion.getDuration());
            batch.draw(texture_explosion, m_explosion.getX() - texture_explosion.getRegionWidth() / 2 + 1, m_explosion.getY() - texture_explosion.getRegionHeight() / 2 + 1);

            if (m_animation_explosion.isAnimationFinished(m_explosion.getDuration())) {
                m_explosion = null;
            } else {
                m_explosion.update(Gdx.graphics.getDeltaTime());
            }
        } else if (randomEvent()) {
            m_explosion = new Event(ThreadLocalRandom.current().nextInt(0, Gdx.graphics.getWidth()), ThreadLocalRandom.current().nextInt(0, Gdx.graphics.getHeight()));

            // TODO : Notify doves
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
        m_texture_explosion.dispose();

        batch.dispose();
    }

    private boolean randomEvent()
    {
        // TODO : Probability
        return true;
    }
}
