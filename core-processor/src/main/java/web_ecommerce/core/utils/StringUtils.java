package web_ecommerce.core.utils;

public class StringUtils {
    public static boolean isNotNullOrEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }
}
