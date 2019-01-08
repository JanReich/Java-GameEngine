package engine.logger;

import engine.toolbox.system.SystemHelper;

import java.util.logging.*;

public class HtmlFormatter extends Formatter {

        // this method is called for every log records
    public String format(LogRecord rec) {

        StringBuffer buf = new StringBuffer(1000);
        buf.append("\t\t\t<tr>\n");

            // colorize any levels >= WARNING in red
        if (rec.getLevel().intValue() >= 1000) {

            buf.append("\t\t\t\t<td style=\"color:red\">");
            buf.append("<b>Error</b>");
        } else if(rec.getLevel().intValue() == 900) {

            buf.append("\t\t\t\t<td style=\"color:orange\">");
            buf.append("<b>Warnung</b>");
        } else {

            buf.append("\t\t\t\t<td>");
            if(rec.getLevel().intValue() == 500)
                buf.append("engine-Info");
            else if(rec.getLevel().intValue() == 400)
                buf.append("Debug-Info");
            else if(rec.getLevel().intValue() == 800)
                buf.append("Info");
        }
        buf.append("</td>\n");
        buf.append("\t\t\t\t<td align=\"center\">");
        buf.append(SystemHelper.calculateDate());
        buf.append("</td>\n");
        buf.append("\t\t\t\t<td>");
        buf.append(formatMessage(rec));
        buf.append("</td>\n");
        buf.append("\t\t\t</tr>\n");

        return buf.toString();
    }

    // this method is called just after the handler using this
    // formatter is created
    public String getHead(Handler h) {

        return "<!DOCTYPE html>\n"
                + "\t<head>\n"
                + "\t\t<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n"
                + "\t\t<style>\n"
                + "\t\t\ttable { width: 100% }\n"
                + "\t\t\tth { font:bold 10pt Tahoma; }\n"
                + "\t\t\ttd { font:normal 10pt Tahoma; }\n"
                + "\t\t\th1 {font:normal 11pt Tahoma;}\n"
                + "\t\t</style>\n"
                + "\t</head>\n"
                + "\t<body>\n"
                + "\t\t<h1> <b>Logs vom: </b><u>" + SystemHelper.getDate() + "</u><b> um: </b><u>" + SystemHelper.getTime() + "</u><b> Uhr</b></h1>\n"
                + "\t\t<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
                + "\t\t\t<tr align=\"left\">\n"
                + "\t\t\t\t<th style=\"width:10%\">Log-Type</th>\n"
                + "\t\t\t\t<th style=\"width:15%\" align=\"center\">Time</th>\n"
                + "\t\t\t\t<th style=\"width:75%\">Log-Message</th>\n"
                + "\t\t\t</tr>\n";
    }

    // this method is called just after the handler using this
    // formatter is closed
    public String getTail(Handler h) {

        return "\t\t</table>\n" +
                "\t\t</br></br>\n" +
                "\t\tKontakt-Mail: Jan.reich@mail.de</br>\n" +
                "\t\tengine by Jan-Philipp Reich aus Dortmund\n" +
                "\t</body>\n" +
                "</html>";
    }
}