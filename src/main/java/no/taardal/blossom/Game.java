package no.taardal.blossom;

import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.input.Mouse;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TestLevel;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = GAME_WIDTH / 16 * 9;
    public static final int SCALE = 3;

    private static final String TITLE = "Nova";
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final int NUMBER_OF_BUFFERS = 3;
    private static final int ONE_SECOND = 1000;
    private static final int ONE_NANOSECOND = 1000000000;
    private static final double UPDATES_PER_SECOND = 60;
    private static final double NANOSECONDS_PER_UPDATE = ONE_NANOSECOND / UPDATES_PER_SECOND;

    private volatile boolean running = false;

    private BufferedImage bufferedImage;
    private Thread gameThread;
    private Camera camera;
    private Keyboard keyboard;
    private Mouse mouse;
    private Level level;

    private Game() {
        setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        bufferedImage = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        gameThread = new Thread(this);
        camera = new Camera(GAME_WIDTH, GAME_HEIGHT);
        keyboard = new Keyboard();
        mouse = new Mouse();
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        level = new TestLevel();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame jframe = new JFrame();
        jframe.add(game);
        jframe.setTitle(TITLE);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.pack();
        game.start();
    }

    @Override
    public void run() {
        int frames = 0;
        int updates = 0;
        float delta = 0;
        long lastTimeMillis = System.currentTimeMillis();
        long lastTimeNano = System.nanoTime();
        while (running) {
            long currentTimeNano = System.nanoTime();
            delta += (currentTimeNano - lastTimeNano) / NANOSECONDS_PER_UPDATE;
            lastTimeNano = currentTimeNano;
            if (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            draw();
            frames++;
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND) {
                lastTimeMillis += ONE_SECOND;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private synchronized void start() {
        requestFocus();
        running = true;
        gameThread.start();
    }

    private synchronized void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            LOGGER.error("Could not join runnable [{}]", gameThread, e);
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    private void update() {
        keyboard.update();
        camera.update(keyboard);
    }

    private void draw() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
            return;
        }

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        level.draw(camera, graphics2D);
        graphics2D.dispose();

        drawBuffer(bufferStrategy);
        bufferStrategy.show();
    }

    private void drawBuffer(BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
    }

}
