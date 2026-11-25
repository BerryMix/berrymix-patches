package app.berrymix.extension;

import java.util.Objects;

@SuppressWarnings("unused")
public class AutoSwitchPatch {

    /**
     * Injection point.
     */
    public static Object getOrDefault(Object key, Object value) {
        String keyString = Objects.toString(key);
        switch (keyString) {
            case "isPremium" -> {
                return Boolean.TRUE;
            }
            case "notPurchasedEventCount" -> {
                return 0;
            }
            default -> {
                return value;
            }
        }
    }

}
