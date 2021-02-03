package engine.toolbox.system.cryptography;

import engine.logger.MyLogger;

import java.security.NoSuchAlgorithmException;

public class MD5 {

  public static String ENCODE(String md5) {
    MyLogger.info("[engine] Ein String wird MD5 verschlüsselt...");
    try {
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : array) {
        sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      MyLogger.fatalError("[<b>engine</b>] Fehler beim MD5 verschlüsseln!");
      return null;
    }
  }
}
