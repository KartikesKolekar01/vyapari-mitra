package com.vyaparimitra.vyapari_mitra.utils;

import java.math.BigDecimal;

public class Constants {

    // Transaction Types
    public static final String TRANSACTION_TYPE_CREDIT = "CREDIT";
    public static final String TRANSACTION_TYPE_PAYMENT = "PAYMENT";

    // Transaction Type Names in Marathi
    public static final String TRANSACTION_TYPE_CREDIT_MR = "उधारी";
    public static final String TRANSACTION_TYPE_PAYMENT_MR = "पैसे भरले";

    // Default Values
    public static final BigDecimal DEFAULT_ZERO = BigDecimal.ZERO;
    public static final int DEFAULT_PAGE_SIZE = 20;

    // Validation Constants
    public static final int MOBILE_LENGTH = 10;
    public static final int PIN_LENGTH = 4;
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final int MAX_FILE_SIZE_MB = 10;

    // API Paths
    public static final String API_BASE = "/api";
    public static final String API_AUTH = API_BASE + "/auth";
    public static final String API_CUSTOMERS = API_BASE + "/customers";
    public static final String API_TRANSACTIONS = API_BASE + "/transactions";
    public static final String API_REPORTS = API_BASE + "/reports";
    public static final String API_FILES = API_BASE + "/files";
    public static final String API_DASHBOARD = API_BASE + "/dashboard";
    public static final String API_SEARCH = API_BASE + "/search";
    public static final String API_REMINDERS = API_BASE + "/reminders";
    public static final String API_SETTINGS = API_BASE + "/settings";

    // Date Formats
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    // Status Codes
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_SERVER_ERROR = 500;

    // Messages
    public static final String MSG_WELCOME = "व्यापारी मित्र मध्ये स्वागत आहे! 🚀";
    public static final String MSG_LOGIN_SUCCESS = "लॉगिन यशस्वी! 🎉";
    public static final String MSG_REGISTER_SUCCESS = "नोंदणी यशस्वी! ✅";
    public static final String MSG_LOGOUT_SUCCESS = "लॉगआउट यशस्वी! 👋";
}