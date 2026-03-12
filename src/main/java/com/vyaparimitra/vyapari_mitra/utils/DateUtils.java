package com.vyaparimitra.vyapari_mitra.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter SQL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "";
    }

    public static String formatSqlDate(LocalDate date) {
        return date != null ? date.format(SQL_DATE_FORMATTER) : "";
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            return dateStr != null ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.DAYS.between(start, end);
    }

    public static boolean isOverdue(LocalDate dueDate) {
        return dueDate != null && dueDate.isBefore(LocalDate.now());
    }

    public static String getMarathiMonth(int month) {
        String[] months = {"जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून",
                "जुलै", "ऑगस्ट", "सप्टेंबर", "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"};
        return months[month - 1];
    }

    public static String getCurrentMarathiDate() {
        LocalDate now = LocalDate.now();
        return now.format(DateTimeFormatter.ofPattern("dd ")) +
                getMarathiMonth(now.getMonthValue()) +
                now.format(DateTimeFormatter.ofPattern(" yyyy"));
    }

    public static String getDayStatus(LocalDate date) {
        if (date == null) return "";
        if (date.isBefore(LocalDate.now())) return "OVERDUE";
        if (date.isEqual(LocalDate.now())) return "TODAY";
        return "UPCOMING";
    }
}