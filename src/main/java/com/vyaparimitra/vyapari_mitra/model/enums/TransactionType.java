package com.vyaparimitra.vyapari_mitra.model.enums;

public enum TransactionType
{
    CREDIT("उधारी"),
    PAYMENT("पैसे भरले");

    private String marathiName;

    TransactionType(String marathiName)
    {
        this.marathiName = marathiName;
    }

    public String getMarathiName()
    {
        return marathiName;
    }
}