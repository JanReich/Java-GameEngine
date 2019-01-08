package gamePackage;

import engine.graphics.Color;
import engine.graphics.Display;
import engine.graphics.interfaces.AdvancedMouseInterface;
import engine.toolbox.math.Point2f;
import engine.toolbox.resourceHelper.DrawHelper;
import engine.toolbox.uiPackage.TextField;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Layout implements AdvancedMouseInterface {

            //Attribute
        private boolean login;
        private boolean hover;

        private boolean loginError;
        private boolean passwordError;

        private boolean mailFieldError;
        private boolean usernameFieldError;
        private boolean passwordFieldError;
        private boolean confirmPasswordFieldError;

            //Referenzen
        private Font textFont;
        private Point2f position;

            //Textfield
        private String errorMessage;
        private TextField loginField;
        private TextField loginPassword;

        private TextField mailField;
        private TextField usernameField;
        private TextField passwordField;
        private TextField confirmPasswordField;

    public Layout(Display display) {

        setUp(display);
    }

    private void setUp(Display display) {

        login = true;
        position = new Point2f(230, 250);
        textFont =  new Font("Roboto", Font.PLAIN, 20);

        loginField = new TextField(display,445);
        loginPassword = new TextField(display,445);

        mailField = new TextField(display,445);
        usernameField = new TextField(display,445);
        passwordField = new TextField(display,445);
        confirmPasswordField = new TextField(display,445);

        display.getActivePanel().drawObjectOnPanel(loginField);
        display.getActivePanel().drawObjectOnPanel(loginPassword);

        display.getActivePanel().drawObjectOnPanel(mailField);
        display.getActivePanel().drawObjectOnPanel(usernameField);
        display.getActivePanel().drawObjectOnPanel(passwordField);
        display.getActivePanel().drawObjectOnPanel(confirmPasswordField);
    }

    @Override
    public void draw(DrawHelper draw) {

        if(login) {

            draw.setColor(Color.WHITE);
            draw.fillRoundRec(position.getX(), position.getY(), 600, 460, 30);
            draw.setColor(Color.DARK_GRAY);
            draw.drawRoundRec(position.getX() - 1, position.getY() - 1, 600, 460, 30);

                //Heading
            draw.setColor(Color.DODGER_BLUE);
            draw.setFont(new Font("Roboto", Font.BOLD, 25));
            draw.drawString("ACCOUNT LOGIN", position.getX() + 50, position.getY() + 85);
            draw.setStroke(2);
            draw.setColor(Color.LIGHT_GRAY);
            draw.drawLine(position.getX() + 47, position.getY() + 89, position.getX() + 259, position.getY() + 89);

                //Email-Field
            draw.setColor(Color.SLATE_GRAY);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("USERNAME:", position.getX() + 50, position.getY() + 147);
            draw.setColor(new Color(225, 225, 225));
            draw.fillRoundRec(position.getX() + 50, position.getY() + 155, 460, 50, 9);

            if (loginError) {

                draw.setColor(Color.INDIAN_RED);
                draw.drawStringBehind(errorMessage, position.getX() + 510, position.getY() + 147);
            } else draw.setColor(Color.GRAY);
            draw.drawRoundRec(position.getX() + 50, position.getY() + 155, 460, 50, 9);

            draw.setFont(textFont);
            draw.setColor(Color.BLACK);
            loginField.setFont(textFont);
            draw.drawString(loginField.getInput().getInputQuerry(), position.getX() + 60, position.getY() + 193);

                //Password-Field
            draw.setColor(Color.GRAY);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("PASSWORD:", position.getX() + 50, position.getY() + 247);
            draw.setColor(new Color(225, 225, 225));
            draw.fillRoundRec(position.getX() + 50, position.getY() + 255, 460, 50, 9);

            if (passwordError) {

                draw.setColor(Color.INDIAN_RED);
                draw.drawStringBehind(errorMessage, position.getX() + 510, position.getY() + 245);
            } else draw.setColor(Color.GRAY);
            draw.drawRoundRec(position.getX() + 50, position.getY() + 255, 460, 50, 9);

            draw.setFont(textFont);
            draw.setColor(Color.BLACK);
            loginPassword.setFont(textFont);

            String password = "";
            for (int i = 0; i < loginPassword.getInput().getInputQuerry().length(); i++) {

                password += "*";
            }
            draw.drawString(password, position.getX() + 60, position.getY() + 293);

                //Forgot-Password
            draw.setColor(Color.DARK_BLUE);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("Passwort vergessen?", position.getX() + 368, position.getY() + 349);

                //Login-Button
            draw.setColor(Color.DODGER_BLUE);
            draw.fillRoundRec(position.getX() + 50, position.getY() + 360, 460, 50, 9);
            draw.setStroke(1);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX() + 50, position.getY() + 360, 460, 50, 9);
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.BOLD, 25));
            draw.drawString("LOG IN", position.getX() + 240, position.getY() + 395);

            if (!hover) {

                draw.setColor(Color.DODGER_BLUE);
                draw.fillRec(position.getX() + 560, position.getY(), 25, 460);
                draw.fillRoundRec(position.getX() + 570, position.getY(), 30, 460, 30);

                draw.setStroke(2);
                draw.setColor(Color.GRAY);
                draw.fillRoundRec(position.getX() + 572, position.getY() + 65, 2, 35, 5);
                draw.fillRoundRec(position.getX() + 585, position.getY() + 65, 2, 35, 5);
            } else {

                draw.setColor(Color.DODGER_BLUE);
                draw.fillRec(position.getX() + 545, position.getY(), 40, 460);
                draw.fillRoundRec(position.getX() + 570, position.getY(), 30, 460, 30);
            }
        } else {

            draw.setColor(Color.DODGER_BLUE);
            draw.fillRoundRec(position.getX(), position.getY(), 600, 610, 30);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX(), position.getY(), 600, 610, 30);

            draw.setColor(new Color(45, 45, 45, 255));
            draw.fillRec(position.getX() + 20, position.getY() - 1, 50, 612);
            draw.fillRoundRec(position.getX(), position.getY() - 1, 35, 612, 30);

                //Heading
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.BOLD, 25));
            draw.drawString("REGISTER ACCOUNT", position.getX() + 130, position.getY() + 85);

            draw.setColor(Color.WHITE);
            draw.fillOval(position.getX() + 480, position.getY() + 50, 50);
            draw.setColor(Color.DODGER_BLUE);
            draw.setStroke(2);
            draw.drawLine(position.getX() + 495, position.getY() + 65, position.getX() + 515, position.getY() + 85);
            draw.drawLine(position.getX() + 495, position.getY() + 85, position.getX() + 515, position.getY() + 65);

                //Username
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("USERNAME:", position.getX() + 130, position.getY() + 145);
            draw.setColor(new Color(72, 118,255));
            draw.fillRoundRec(position.getX() + 130, position.getY() + 155, 400, 50, 9);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX() + 130, position.getY() + 155, 400, 50, 9);

                draw.setFont(textFont);
                draw.setColor(Color.BLACK);
                usernameField.setFont(textFont);
                draw.drawString(usernameField.getInput().getInputQuerry(), position.getX() + 140, position.getY() + 193);

                if(usernameFieldError) {

                    draw.setColor(Color.INDIAN_RED);
                    draw.setFont(new Font("Roboto", Font.PLAIN, 15));
                    draw.drawStringBehind(errorMessage, position.getX() + 510, position.getY() + 145);
                }

                //Password
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("PASSWORD:", position.getX() + 130, position.getY() + 245);
            draw.setColor(new Color(72, 118,255));
            draw.fillRoundRec(position.getX() + 130, position.getY() + 255, 400, 50, 9);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX() + 130, position.getY() + 255, 400, 50, 9);

                draw.setFont(textFont);
                draw.setColor(Color.BLACK);
                passwordField.setFont(textFont);

                String password = "";
                for (int i = 0; i < passwordField.getInput().getInputQuerry().length(); i++)
                    password += "*";
                draw.drawString(password, position.getX() + 140, position.getY() + 293);

                //Confirm Password
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("CONFIRM PASSWORD:", position.getX() + 130, position.getY() + 345);
            draw.setColor(new Color(72, 118,255));
            draw.fillRoundRec(position.getX() + 130, position.getY() + 355, 400, 50, 9);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX() + 130, position.getY() + 355, 400, 50, 9);

                draw.setFont(textFont);
                draw.setColor(Color.BLACK);
                confirmPasswordField.setFont(textFont);

                password = "";
                for (int i = 0; i < confirmPasswordField.getInput().getInputQuerry().length(); i++)
                    password += "*";
                draw.drawString(password, position.getX() + 140, position.getY() + 393);

                //E-mail
            draw.setColor(Color.WHITE);
            draw.setFont(new Font("Roboto", Font.PLAIN, 15));
            draw.drawString("EMAIL ADDRESS:", position.getX() + 130, position.getY() + 445);
            draw.setColor(new Color(72, 118,255));
            draw.fillRoundRec(position.getX() + 130, position.getY() + 455, 400, 50, 9);
            draw.setColor(Color.ROYAL_BLUE);
            draw.drawRoundRec(position.getX() + 130, position.getY() + 455, 400, 50, 9);

                draw.setFont(textFont);
                draw.setColor(Color.BLACK);
                mailField.setFont(textFont);
                draw.setFont(new Font("Roboto", Font.PLAIN, 20));
                draw.drawString(mailField.getInput().getInputQuerry() + "", position.getX() + 140, position.getY() + 493);

            draw.setColor(Color.WHITE);
            draw.fillRoundRec(position.getX() + 130, position.getY() + 530,400, 50, 9);
            draw.setColor(Color.LIGHT_GRAY);
            draw.drawRoundRec(position.getX() + 130, position.getY() + 530,400, 50, 9);
            draw.setColor(Color.DODGER_BLUE);
            draw.setFont(new Font("Roboto", Font.BOLD, 25));
            draw.drawString("REGISTER", position.getX() + 270, position.getY() + 565);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

            //Informationen
        if(login) {

            if(e.getX() > position.getX() + 50 && e.getX() < position.getX() + 510 && e.getY() > position.getY() + 155 && e.getY() < position.getY() + 205) {

                loginField.setTyping(true);
                loginPassword.setTyping(false);
            } else if(e.getX() > position.getX() + 50 && e.getX() < position.getX() + 510 && e.getY() > position.getY() + 255 && e.getY() < position.getY() + 305) {

                loginField.setTyping(false);
                loginPassword.setTyping(true);
            } else {

                loginField.setTyping(false);
                loginPassword.setTyping(false);
            }
        } else {

            if(e.getX() > position.getX() + 130 && e.getX() < position.getX() + 530 && e.getY() > position.getY() + 155 && e.getY() < position.getY() + 205) {

                mailField.getInput().setTyping(false);
                usernameField.getInput().setTyping(true);
                passwordField.getInput().setTyping(false);
                confirmPasswordField.getInput().setTyping(false);
            } else if(e.getX() > position.getX() + 130 && e.getX() < position.getX() + 530 && e.getY() > position.getY() + 155 && e.getY() < position.getY() + 305) {

                mailField.getInput().setTyping(false);
                usernameField.getInput().setTyping(false);
                passwordField.getInput().setTyping(true);
                confirmPasswordField.getInput().setTyping(false);
            } else if(e.getX() > position.getX() + 130 && e.getX() < position.getX() + 530 && e.getY() > position.getY() + 155 && e.getY() < position.getY() + 405) {

                mailField.getInput().setTyping(false);
                usernameField.getInput().setTyping(false);
                passwordField.getInput().setTyping(false);
                confirmPasswordField.getInput().setTyping(true);
            } else if(e.getX() > position.getX() + 130 && e.getX() < position.getX() + 530 && e.getY() > position.getY() + 155 && e.getY() < position.getY() + 505) {

                mailField.getInput().setTyping(true);
                usernameField.getInput().setTyping(false);
                passwordField.getInput().setTyping(false);
                confirmPasswordField.getInput().setTyping(false);
            } else {

                mailField.getInput().setTyping(false);
                usernameField.getInput().setTyping(false);
                passwordField.getInput().setTyping(false);
                confirmPasswordField.getInput().setTyping(false);
            }
        }

        if(e.getX() > position.getX() + 545 && e.getX() < position.getX() + 600 && e.getY() > position.getY() && e.getY() < position.getY() + 600 && login) {

            login = false;
            loginField.setTyping(false);
            loginPassword.setTyping(false);

            mailFieldError = false;
            usernameFieldError = false;
            passwordFieldError = false;
            confirmPasswordFieldError = false;

            loginField.setInputQuerry("");
            loginPassword.setInputQuerry("");
        } else if(e.getX() > position.getX() + 480 && e.getX() < position.getX() + 530 && e.getY() > position.getY() + 50 && e.getY() < position.getY() + 100 && !login) {

            login = true;
            mailField.setTyping(false);
            usernameField.setTyping(false);
            passwordField.setTyping(false);
            confirmPasswordField.setTyping(false);

            loginError = false;
            passwordError = false;

            usernameField.setInputQuerry("");
            passwordField.setInputQuerry("");
            confirmPasswordField.setInputQuerry("");
            mailField.setInputQuerry("");
        }

            //Falls der Login-Button gedrückt wurde
        if(login) {

            if(e.getX() > position.getX() + 50 && e.getX() < position.getX() + 510 && e.getY() > position.getY() + 360 && e.getY() < position.getY() + 410 && login) {

                if(loginField.getInput().getInputQuerry().length() == 0 && loginPassword.getInput().getInputQuerry().length() == 0) {

                    loginError = true;
                    passwordError = true;
                    errorMessage = "Feld darf nicht leer bleiben";
                } else if(loginField.getInput().getInputQuerry().length() == 0 && loginPassword.getInput().getInputQuerry().length() != 0) {

                    loginError = true;
                    passwordError = false;
                    errorMessage = "Feld darf nicht leer bleiben";
                } else if(loginField.getInput().getInputQuerry().length() != 0 && loginPassword.getInput().getInputQuerry().length() == 0) {

                    loginError = false;
                    passwordError = true;
                    errorMessage = "Feld darf nicht leer bleiben";
                } else {


                }
            }
        }

            //False der Register-Button gedrückt wurde
        else {


        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(login) {

            if(e.getX() > position.getX() + 560 && e.getX() < position.getX() + 600 && e.getY() > position.getY() && e.getY() < position.getY() + 600) hover = true;
            else if(e.getX() > position.getX() + 545 && e.getX() < position.getX() + 600 && e.getY() > position.getY() && e.getY() < position.getY() + 600) hover = true;
            else hover = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}