package com.vyaparimitra.vyapari_mitra.utils;

import java.util.HashMap;
import java.util.Map;

public class MarathiMessages {

    private static final Map<String, String> MESSAGES = new HashMap<>();

    static {
        // Success messages
        MESSAGES.put("customer.added", "ग्राहक यशस्वीरित्या जोडला गेला! ✅");
        MESSAGES.put("customer.updated", "ग्राहक माहिती अद्यावत केली! ✏️");
        MESSAGES.put("customer.deleted", "ग्राहक हटवला गेला! 🗑️");

        MESSAGES.put("credit.added", "उधारी यशस्वीरित्या नोंदली! 📝");
        MESSAGES.put("payment.added", "पैसे यशस्वीरित्या नोंदले! 💰");

        MESSAGES.put("file.uploaded", "फाइल अपलोड झाली! 📸");
        MESSAGES.put("file.deleted", "फाइल हटवली गेली! 🗑️");

        MESSAGES.put("login.success", "लॉगिन यशस्वी! 🚀");
        MESSAGES.put("register.success", "नोंदणी यशस्वी! 🎉");

        // Error messages
        MESSAGES.put("error.customer.notfound", "ग्राहक सापडला नाही! ❌");
        MESSAGES.put("error.transaction.notfound", "व्यवहार सापडला नाही! ❌");
        MESSAGES.put("error.owner.notfound", "मालक सापडला नाही! कृपया प्रथम नोंदणी करा.");

        MESSAGES.put("error.invalid.pin", "चुकीचा पिन! 🔐");
        MESSAGES.put("error.invalid.mobile", "चुकीचा मोबाईल नंबर! 📱");

        MESSAGES.put("error.balance.exceed", "भरलेली रक्कम उधारीपेक्षा जास्त असू शकत नाही! ⚠️");
        MESSAGES.put("error.amount.positive", "रक्कम ० पेक्षा जास्त असणे आवश्यक आहे! 💵");

        MESSAGES.put("error.file.size", "फाइल साइझ १०MB पेक्षा कमी असावी! 📁");
        MESSAGES.put("error.file.type", "कृपया फक्त इमेज फाइल अपलोड करा! 🖼️");

        MESSAGES.put("error.unauthorized", "कृपया प्रथम लॉगिन करा! 🔑");
        MESSAGES.put("error.server", "सर्व्हर त्रुटी! कृपया नंतर प्रयत्न करा.");
    }

    public static String get(String key) {
        return MESSAGES.getOrDefault(key, key);
    }

    public static String get(String key, Object... args) {
        String message = MESSAGES.getOrDefault(key, key);
        return String.format(message, args);
    }

    public static Map<String, String> getAllMessages() {
        return new HashMap<>(MESSAGES);
    }
}