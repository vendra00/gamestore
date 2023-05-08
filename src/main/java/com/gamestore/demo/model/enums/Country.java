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
    OTHER;

    public static List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }
}
