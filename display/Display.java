package display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable, Displayable {
    private Thread thread;
    private JFrame frame;
    private static boolean running = false;
    private final int fps;

    protected String title;
    protected final int WIDTH, HEIGHT;
    protected Mouse mouse;

    // System methods
    public Display(String title, int width, int height, int fps) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.fps = fps;
        this.title = title;

        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);

        this.frame.setTitle(this.title);
        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setAlwaysOnTop(true);

        this.start();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "display.Display");
        this.thread.start();
    }

    public synchronized void stop() {
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / this.fps;
        double delta = 0;
        int frames = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                fixedUpdate();
                delta--;
            }
            update();
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(this.title + " | " + frames + " fps");
                frames = 0;
            }
        }
        stop();
    }

    // Control methods
    public void init() {
        // Called on start
    }

    public void update() {
        // Called every frame
    }

    public void fixedUpdate() {
        // Called at 60 fps
    }

    public void draw(Graphics g) {
        // Draw to canvas
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g.setColor(new Color(22, 22, 22));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Start Rendering
        this.draw(g);

        // End Rendering
        g.dispose();
        bs.show();
    }
}

