package engine.toolbox.system;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class Clipboard implements ClipboardOwner {

            //Attribute

            //Referenzen


    public Clipboard() {


    }

    @Override
    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents) {

    }


    public void setClipboardContents(String string) {

        StringSelection stringSelection = new StringSelection(string);
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    public String getClipboardContents() {

        String result = "";
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);

        if (hasTransferableText) {
            try {

                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException | IOException e) {

                e.printStackTrace();
            }
        }
        return result;
    }
}
