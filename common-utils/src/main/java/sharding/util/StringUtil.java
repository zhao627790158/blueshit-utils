package sharding.util;

import com.google.common.base.Splitter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

    /**
     * Adjust is boolean value or not.
     *
     * @param value to be adjusted string value
     * @return is boolean value or not
     */
    public static boolean isBooleanValue(final String value) {
        return Boolean.TRUE.toString().equalsIgnoreCase(value) || Boolean.FALSE.toString().equalsIgnoreCase(value);
    }

    /**
     * Adjust is int value or not.
     *
     * @param value to be adjusted string value
     * @return is int value or not
     */
    public static boolean isIntValue(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (final NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Adjust is long value or not.
     *
     * @param value to be adjusted string value
     * @return is long value or not
     */
    public static boolean isLongValue(final String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (final NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Split string value to list by comma delimiter.
     *
     * @param value to be split string value
     * @return split list
     */
    public static List<String> splitWithComma(final String value) {
        return Splitter.on(",").trimResults().splitToList(value);
    }
}
