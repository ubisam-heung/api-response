package com.github.ubisam_heung.api_response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionLogUtil {
    public static String getKoreanTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년_M월_d일 a h시_mm분_ss초").withLocale(java.util.Locale.KOREAN);
        String formatted = now.format(formatter)
            .replace("AM", "오전")
            .replace("PM", "오후");
        return "[" + formatted + "]";
    }

    public static String formatLog(String handler, Exception ex) {
        return String.format("\n%s [%s] %s: %s\n", getKoreanTimestamp(), handler, ex.getClass().getSimpleName(), ex.getMessage());
    }

    public static String formatLog(String handler, String exClass, String message) {
        return String.format("\n%s [%s] %s: %s\n", getKoreanTimestamp(), handler, exClass, message);
    }
}