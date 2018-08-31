package trace.api;

import java.util.UUID;

public class IdGen {
    public static long get() {
        UUID uuid = UUID.randomUUID();
        return uuid.getLeastSignificantBits() ^ uuid.getMostSignificantBits();
    }
}
