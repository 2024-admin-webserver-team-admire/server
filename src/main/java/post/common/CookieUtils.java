package post.common;

import jakarta.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class CookieUtils {

    public static List<String> getValuesFromCookie(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    String value = cookie.getValue();
                    return new ArrayList<>(List.of(value.split(",")));
                }
            }
        }
        return new ArrayList<>();
    }

    public static String addValueToCookie(String newValue, List<String> existingValues) {
        if (!existingValues.contains(newValue)) {
            existingValues.add(newValue);
        }
        return String.join(",", existingValues);
    }
}
