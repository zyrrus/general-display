package display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable, Displayable {
    private Thread thread;
    private JFrame frame;
    private static boolean running = false;
    private final int fps;

    // Listeners
    protected Mouse mouse;
    protected Keyboard keyboard;

    // Flags
    protected boolean antialiasingON = true;
    protected boolean showFPS = true;
    protected boolean isResizable = false;
    protected boolean isAlwaysOnTop = true;

    // Settings
    protected Color bgColor = new Color(22, 22, 22);
    protected String title;
    protected final int WIDTH, HEIGHT;


    // System methods
    public Display(String title, int width, int height, int fps) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.fps = fps;
        this.title = title;

        this.frame = new JFrame();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        // Add mouse
        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);

        // Add keyboard
        this.keyboard = new Keyboard();
        this.addKeyListener(this.keyboard);

        this.frame.setTitle(this.title);
        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(this.isResizable);
        this.frame.setVisible(true);
        this.frame.setAlwaysOnTop(this.isAlwaysOnTop);

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
                if (this.showFPS) this.setTitle(this.title + " | " + frames + " fps");
                frames = 0;
            }
        }
        stop();
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        if (this.antialiasingON) g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g.setColor(this.bgColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Start Rendering
        this.draw(g);

        // End Rendering
        g.dispose();
        bs.show();
    }

    // Control methods
    public void init() {}
    public void update() {}
    public void fixedUpdate() {}
    public void draw(Graphics g) {}

    public void setTitle(String s) { this.frame.setTitle(s); }
}

