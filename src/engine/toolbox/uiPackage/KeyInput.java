package engine.toolbox.uiPackage;

import engine.graphics.interfaces.KeyInterface;
import engine.toolbox.resourceHelper.DrawHelper;
import engine.toolbox.system.Clipboard;

import java.awt.event.KeyEvent;

public class KeyInput implements KeyInterface {

            //Attribute
        private boolean alt;
        private boolean ctrl;
        private boolean shift;

        private boolean limit;
        private boolean typing;
        private boolean amountOfChars;

        private int maxAmountOfChars;

            //Referenzen
        private String inputQuerry;
        private Clipboard clipboard;

    public KeyInput() {

        this.inputQuerry = "";
        clipboard = new Clipboard();
    }

    public KeyInput(boolean typing) {

        this();
        this.typing = typing;
    }

    @Override
    public void keyPressed(KeyEvent e) {

            //Attribute überprüfen
        if(e.getKeyCode() == KeyEvent.VK_ALT)
            alt = true;
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
            ctrl = true;
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            shift = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        //Attribute überprüfen
        if(e.getKeyCode() == KeyEvent.VK_ALT)
            alt = false;
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
            ctrl = false;
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            shift = false;

        if(typing) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_A:
                    appendToString('a');
                    break;
                case KeyEvent.VK_B:
                    appendToString('b');
                    break;
                case KeyEvent.VK_C:

                    appendToString('c');
                    break;
                case KeyEvent.VK_D:

                    appendToString('d');
                    break;
                case KeyEvent.VK_E:

                    appendToString('e');
                    break;
                case KeyEvent.VK_F:

                    appendToString('f');
                    break;
                case KeyEvent.VK_G:

                    appendToString('g');
                    break;
                case KeyEvent.VK_H:

                    appendToString('h');
                    break;
                case KeyEvent.VK_I:

                    appendToString('i');
                    break;
                case KeyEvent.VK_J:

                    appendToString('j');
                    break;
                case KeyEvent.VK_K:

                    appendToString('k');
                    break;
                case KeyEvent.VK_L:

                    appendToString('l');
                    break;
                case KeyEvent.VK_M:

                    appendToString('m');
                    break;
                case KeyEvent.VK_N:

                    appendToString('n');
                    break;
                case KeyEvent.VK_O:

                    appendToString('o');
                    break;
                case KeyEvent.VK_P:

                    appendToString('p');
                    break;
                case KeyEvent.VK_Q:

                    if(alt && ctrl) appendToString('@');
                    else appendToString('q');
                    break;
                case KeyEvent.VK_R:

                    appendToString('r');
                    break;
                case KeyEvent.VK_S:

                    appendToString('s');
                    break;
                case KeyEvent.VK_T:

                    appendToString('t');
                    break;
                case KeyEvent.VK_U:

                    appendToString('u');
                    break;
                case KeyEvent.VK_V:

                    if(ctrl)
                        appendToString(clipboard.getClipboardContents());
                    else
                        appendToString('v');
                    break;
                case KeyEvent.VK_W:

                    appendToString('w');
                    break;
                case KeyEvent.VK_X:

                    appendToString('x');
                    break;
                case KeyEvent.VK_Y:

                    appendToString('y');
                    break;
                case KeyEvent.VK_Z:

                    appendToString('z');
                    break;
                case KeyEvent.VK_0:
                    appendToString('0');
                    break;
                case KeyEvent.VK_1:

                    appendToString('1');
                    break;
                case KeyEvent.VK_2:

                    appendToString('2');
                    break;
                case KeyEvent.VK_3:

                    appendToString('3');
                    break;
                case KeyEvent.VK_4:

                    appendToString('4');
                    break;
                case KeyEvent.VK_5:

                    appendToString('5');
                    break;
                case KeyEvent.VK_6:

                    appendToString('6');
                    break;
                case KeyEvent.VK_7:

                    appendToString('7');
                    break;
                case KeyEvent.VK_8:

                    appendToString('8');
                    break;
                case KeyEvent.VK_9:

                    appendToString('9');
                    break;
                case KeyEvent.VK_SPACE:

                    appendToString(' ');
                    break;
                case KeyEvent.VK_BACK_SPACE:

                    if(inputQuerry.length() >= 1 && typing)
                        inputQuerry = inputQuerry.substring(0, inputQuerry.length() - 1);
                    break;
                case KeyEvent.VK_PERIOD:    //Punkt

                    appendToString('.');
                    break;
            }
        }
    }

    public void removeLetter() {

        if(inputQuerry.length() >= 1 && typing)
            inputQuerry = inputQuerry.substring(0, inputQuerry.length() - 1);
    }

    public void appendToString(char c) {

        if(limit) {

            if (inputQuerry.length() < maxAmountOfChars) add(c);
        } else add(c);
    }

    public void appendToString(String s) {

        char[] letters = s.toCharArray();

        for (int i = 0; i < letters.length; i++) {

            appendToString(letters[i]);
        }
    }

    private void add(char c) {

        String s = "";
        s += c;

        if(limit) {

            if(inputQuerry.length() < maxAmountOfChars)
                if(shift) inputQuerry += s.toUpperCase();
                else inputQuerry += s;
        } else if(shift) inputQuerry += s.toUpperCase();
        else inputQuerry += s;
    }

    @Override
    public void draw(DrawHelper draw) {

    }

            //---------- GETTER AND SETTER ----------
    public boolean isTyping() {

        return typing;
    }

    public String getInputQuerry() {

        return inputQuerry;
    }

    public void setTyping(boolean typing) {

        this.typing = typing;
    }

    public void setInputQuerry(String inputQuerry) {

        this.inputQuerry = inputQuerry;
    }
}
