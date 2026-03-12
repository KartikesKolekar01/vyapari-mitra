package com.vyaparimitra.vyapari_mitra.model.enums;


public enum PaymentStatus
{
    PENDING("बाकी"),
    PARTIAL("अर्धवट"),
    COMPLETED("पूर्ण");

    private String marathiName;

    PaymentStatus(String marathiName)
    {
        this.marathiName = marathiName;
    }

    public String getMarathiName()
    {
        return marathiName;
    }
}