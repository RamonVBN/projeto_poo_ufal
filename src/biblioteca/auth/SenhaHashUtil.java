
package biblioteca.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SenhaHashUtil {

    private static final String SALT_FIXO = "BibliotecaUFAL2026";
    private static final int ITERACOES = 600_000;
    private static final int TAMANHO_HASH = 256;

    public static String hash(String senha)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = SALT_FIXO.getBytes(StandardCharsets.UTF_8);

        PBEKeySpec spec = new PBEKeySpec(
                senha.toCharArray(),
                salt,
                ITERACOES,
                TAMANHO_HASH
        );

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } finally {
            spec.clearPassword();
        }
    }
}
