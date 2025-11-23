import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESGCM {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        try {
            SecretKey secretKey = generateKey();
            String textEncry = encrypt(secretKey, "Asela".getBytes(StandardCharsets.UTF_8));
            System.out.println(textEncry);
            String textDecry = decrypt(secretKey, textEncry);
            System.out.println(textDecry);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    /**
     * Encryption
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(SecretKey key, byte[] data) throws Exception {
        // 1. Use standard 12 bytes for GCM IV (Standard NIST recommendation)
        byte[] initVector = new byte[12];
        new SecureRandom().nextBytes(initVector);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, initVector);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        //  1: Change DECRYPT_MODE to ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        //  2: Pass the 'data' into doFinal to actually encrypt it
        byte[] cipherText = cipher.doFinal(data);

        //  3: Calculate size based on IV + Ciphertext (not data)
        byte[] out = new byte[initVector.length + cipherText.length];

        //  4: Copy IV first (Transport header)
        System.arraycopy(initVector, 0, out, 0, initVector.length);

        //  5: Copy Ciphertext next (Encrypted body)
        System.arraycopy(cipherText, 0, out, initVector.length, cipherText.length);

        return Base64.getEncoder().encodeToString(out);
    }


    /**
     * Decrypt
     * @param key
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(SecretKey key, String cipherText) throws Exception {
        // 1. Decode the
        byte[] decoded = Base64.getDecoder().decode(cipherText);

        // 2. Split the IV in the first 12 bytes
        byte[] iv = new byte[12];
        System.arraycopy(decoded, 0, iv, 0, 12);

        // 3. Rest is the cipherText
        byte[] cipherTex = new byte[decoded.length - iv.length];
        System.arraycopy(decoded, iv.length, cipherTex, 0, cipherTex.length);

        // 4. Configure for decryption
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        // 5. Decrypt
        byte[] decrypted = cipher.doFinal(cipherTex);

        // 6. Return as string
        return new String(decrypted, StandardCharsets.UTF_8);

    }
}
