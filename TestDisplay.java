import display.Display;
import display.Displayable;

import java.awt.*;

public class TestDisplay extends Display implements Displayable {
    private int xPos = 0, yPos = 0;

    public TestDisplay(int width, int height) {
        super("Test Display", width, height, 60);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        this.xPos = this.mouse.getX();
        this.yPos = this.mouse.getY();
    }

    @Override
    public void fixedUpdate() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.drawOval(this.xPos - 15, this.yPos - 15, 30, 30);
    }
}

