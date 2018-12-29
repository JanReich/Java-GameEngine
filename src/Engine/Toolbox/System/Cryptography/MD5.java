package Engine.Toolbox.System.Cryptography;

import Engine.Logger.MyLogger;

import java.security.NoSuchAlgorithmException;

public class MD5 {

            //Attribute

            //Referenzen

    public static String ENCODE(String md5) {

        MyLogger.info("[Engine] Ein String wird MD5 verschlüsselt...");
        try {

            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i)
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            MyLogger.fatalError("[<b>Engine</b>] Fehler beim MD5 verschlüsseln!");
            return null;
        }
    }
}
