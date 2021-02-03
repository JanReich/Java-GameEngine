package engine.toolbox.system.cryptography;

import engine.logger.MyLogger;
import engine.toolbox.math.SimpleMath;
import lombok.Getter;

import java.math.BigInteger;

public class RSA {

  private long d = 0;
  private final long p;
  private final long q;

  @Getter
  private String publicKey;
  @Getter
  private String privateKey;

  public RSA() {
    MyLogger.engineInformation("[engine-RSA] Verschlüsselung wird vorbereitet...");
    p = SimpleMath.generatePrime((int) (Math.random() * 1000) + 2000);
    q = SimpleMath.generatePrime((int) (Math.random() * 1000) + 1000);
    generateKeys();
  }

  private void generateKeys() {
    //public Key (N und e)
    MyLogger.engineInformation("[engine-RSA] PublicKey und PrivateKey werden generiert...");
    long n = p * q;
    long phiN = (p - 1) * (q - 1);
    long e = SimpleMath.generatePrime((int) (Math.random() * 1000));
    if (phiN % e == 0) {
      generateKeys();
      return;
    }
    //private Key (N und d)
    for (; true; d++) {
      if (((e * d) % phiN == 1)) {
        break;
      }
    }
    publicKey = "PublicKey: e: " + e + ": n: " + n;
    privateKey = "PrivateKey: d: " + d + ": n: " + n;
    MyLogger.engineInformation("[engine-RSA] PublicKey und PrivateKey wurden erfolgreich generiert!");
  }

  public String encryptMessage(final String message, final String publicKey) {
    //Format: PublicKey: e: [e]: n: [n]
    String encryptedMessage = "";
    String[] key = publicKey.split(": ");
    long e = Integer.parseInt(key[2]);
    long n = Integer.parseInt(key[4]);
    char[] letters = message.toCharArray();
    for (char letter : letters) {
      encryptedMessage += encrypt(charToInt(letter), e, n) + " ";
    }
    return encryptedMessage;
  }

  public String decryptMessage(final String message, final String privateKey) {
    //Format: PrivateKey: d: [d]: n: [n]
    String decryptedMessage = "";
    String[] key = privateKey.split(": ");
    long d = Integer.parseInt(key[2]);
    long n = Integer.parseInt(key[4]);
    String[] encryptedLetters = message.split(" ");
    for (String letter : encryptedLetters) {
      decryptedMessage += intToChar(Integer.parseInt(decrypt(Integer.parseInt(letter), d, n)));
    }
    return decryptedMessage;
  }

  private int charToInt(final char value) {
    return value;
  }

  private char intToChar(final int value) {
    return (char) value;
  }

  private BigInteger encrypt(final long text, final long e, final long n) {
    BigInteger textBig = new BigInteger(text + "");
    BigInteger eBig = new BigInteger(e + "");
    BigInteger nBig = new BigInteger(n + "");
    return textBig.modPow(eBig, nBig);
  }

  private String decrypt(long text, long d, long n) {
    BigInteger textBig = new BigInteger(text + "");
    BigInteger dBig = new BigInteger(d + "");
    BigInteger nBig = new BigInteger(n + "");
    BigInteger message = textBig.modPow(dBig, nBig);
    return message.toString();
  }
}
