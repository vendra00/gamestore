package com.gamestore.demo.model.enums;

import java.util.Arrays;
import java.util.List;

public enum Country {
    USA,
    CANADA,
    UK,
    AUSTRALIA,
    JAPAN,
    GERMANY,
    FRANCE,
    SPAIN,
    ITALY,
    BRAZIL,
    ARGENTINA,
    AUSTRIA,
    BELGIUM,
    BULGARIA,
    CHILE,
    CHINA,
    COLOMBIA,
    CROATIA,
    CZECH_REPUBLIC,
    DENMARK,
    EGYPT,
    FINLAND,
    GREECE,
    HONG_KONG,
    HUNGARY,
    INDIA,
    INDONESIA,
    ENGLAND,
    MEXICO,
    NETHERLANDS,
    NEW_ZEALAND,
    NORWAY,
    POLAND,
    PARAGUAY,
    URUGUAY,
    PORTUGAL,
    ROMANIA,
    RUSSIA,
    SAUDI_ARABIA,
    SINGAPORE,
    SOUTH_AFRICA,
    SOUTH_KOREA,
    SWEDEN,
    SWITZERLAND,
    TAIWAN,
    THAILAND,
    TURKEY,
    UKRAINE,
    UNITED_ARAB_EMIRATES,
    VENEZUELA,
    VIETNAM,
    OTHER;

    public static List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }
}
