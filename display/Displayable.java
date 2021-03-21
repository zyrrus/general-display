package display;

import java.awt.*;

public interface Displayable {
    void init();
    void update();
    void fixedUpdate();
    void draw(Graphics g);

}
