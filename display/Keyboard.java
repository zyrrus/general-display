package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard implements KeyListener {
    private ArrayList<KeyEvent> curKeys = new ArrayList<>();

    public ArrayList<KeyEvent> getKeys() {
        return curKeys;
    }

    public String getText() {
        char[] keys = new char[this.curKeys.size()];
        for (int i = 0; i < keys.length; i++)
            keys[i] = this.curKeys.get(i).getKeyChar();

        System.out.println(keys);
        return new String(keys);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
            if (this.curKeys.size() > 0) this.curKeys.remove(this.curKeys.size() - 1);
        }
        else this.curKeys.add(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
