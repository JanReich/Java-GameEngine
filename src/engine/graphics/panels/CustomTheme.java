package Engine.Graphics.Panels;

import Engine.Toolbox.ResourceHelper.FileHelper;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class CustomTheme extends DefaultMetalTheme {

        private String themeName = "";
        private ColorUIResource primary1;
        private ColorUIResource primary2;
        private ColorUIResource primary3;

        private ColorUIResource secondary1;
        private ColorUIResource secondary2;
        private ColorUIResource secondary3;

        private ColorUIResource black;
        private ColorUIResource white;

        private FontUIResource windowTitleFont;
        private FontUIResource controlFont;

    public CustomTheme(File file) {

        defaultColors();
        defaultFonts();
        loadProperties(file);
    }

    private void defaultFonts() {

        controlFont = super.getControlTextFont();
        windowTitleFont = super.getWindowTitleFont();
    }

    private void defaultColors() {

        primary1 = super.getPrimary1();
        primary2 = super.getPrimary2();
        primary3 = super.getPrimary3();

        secondary1 = super.getSecondary1();
        secondary2 = super.getSecondary2();
        secondary3 = super.getSecondary3();

        black = super.getBlack();
        white = super.getWhite();
    }

    private void loadProperties(File file) {

            //Request the name of the Template
        String temp = FileHelper.getProperty(file, "name");
        if(null != temp) themeName = temp;

            //Request primary colors
        temp = FileHelper.getProperty(file, "primary1");
        if(null != temp) primary1 = parseColor(temp);
        temp = FileHelper.getProperty(file, "primary2");
        if(null != temp) primary2 = parseColor(temp);
        temp = FileHelper.getProperty(file, "primary3");
        if(null != temp) primary3 = parseColor(temp);

            //Request secondary colors
        temp = FileHelper.getProperty(file, "secondary1");
        if(null != temp) secondary1 = parseColor(temp);
        temp = FileHelper.getProperty(file, "secondary2");
        if(null != temp) secondary2 = parseColor(temp);
        temp = FileHelper.getProperty(file, "secondary3");
        if(null != temp) secondary3 = parseColor(temp);

            //Request Black && White
        temp = FileHelper.getProperty(file, "black");
        if(null != temp) black = parseColor(temp);
        temp = FileHelper.getProperty(file, "white");
        if(null != temp)  white = parseColor(temp);

        temp = FileHelper.getProperty(file, "titlefont");
        if(null != temp) windowTitleFont = parseFont(temp);
        temp = FileHelper.getProperty(file, "controlfont");
        if(null != temp) controlFont = parseFont(temp);
    }

    private ColorUIResource parseColor(String color) {

        int r, g, b;
        r = g = b = 0;

        try {

            StringTokenizer stok = new StringTokenizer(color, ",");
            r = Integer.parseInt(stok.nextToken());
            g = Integer.parseInt(stok.nextToken());
            b = Integer.parseInt(stok.nextToken());
        } catch(Exception e) {

            System.out.println(e);
            System.out.println("Im Theme " + themeName + " gab es einen Fehler beim auslesen folgender Farbe: " + color);
        } return new ColorUIResource(r, g, b);
    }

    private FontUIResource parseFont(String font) {

        String szName = "Dialog";
        int iSize = 12, iStyle = Font.PLAIN;

        try {

            StringTokenizer stok = new StringTokenizer(font, ",");
            szName 	= stok.nextToken();
            String tmp 	= stok.nextToken();
            iSize 	= Integer.parseInt(stok.nextToken());

            if(tmp.equalsIgnoreCase("Font.BOLD")) iStyle = Font.BOLD;
            else if(tmp.equalsIgnoreCase("Font.ITALIC")) iStyle = Font.ITALIC;
            else if(tmp.equalsIgnoreCase("Font.BOLD|Font.ITALIC") || tmp.equalsIgnoreCase("Font.ITALIC|Font.BOLD")) iStyle = Font.BOLD|Font.ITALIC;
        } catch(Exception e) {

            System.out.println(e);
            System.out.println("Im Theme " + themeName + " gab es einen Fehler beim auslesen folgender Farbe: " + font);
        } return new FontUIResource(szName,iStyle,iSize);
    }

    public String getName() {

        return themeName;
    }

    protected ColorUIResource getPrimary1() {

        return primary1;
    }
    protected ColorUIResource getPrimary2() {

        return primary2;
    }
    protected ColorUIResource getPrimary3() {

        return primary3;
    }

    protected ColorUIResource getSecondary1() {

        return secondary1;
    }
    protected ColorUIResource getSecondary2() {

        return secondary2;
    }
    protected ColorUIResource getSecondary3() {

        return secondary3;
    }

    protected ColorUIResource getBlack() {

        return black;
    }
    protected ColorUIResource getWhite() {

        return white;
    }

    public FontUIResource getWindowTitleFont() {

        return windowTitleFont;
    }
    public FontUIResource getMenuTextFont() {

        return controlFont;
    }
    public FontUIResource getControlTextFont() {

        return controlFont;
    }

}