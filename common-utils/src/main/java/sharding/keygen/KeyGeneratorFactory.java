package sharding.keygen;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by zhaoheng on 18/10/17.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyGeneratorFactory {

    public static KeyGenerator newInstance(final String keyGeneratorClassName) {
        try {
            return (KeyGenerator) Class.forName(keyGeneratorClassName).newInstance();
        } catch (final ReflectiveOperationException ex) {
            throw new IllegalArgumentException(String.format("Class %s should have public privilege and no argument constructor", keyGeneratorClassName));
        }
    }
}
