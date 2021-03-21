import display.Display;
import display.Displayable;

import java.awt.*;

public class TestDisplay extends Display implements Displayable {

    public TestDisplay(String title, int width, int height, int fps) {
        super(title, width, height, fps);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void fixedUpdate() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.drawOval(200, 50, 30, 30);
    }
}
