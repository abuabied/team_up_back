package com.abuabied.teamUp.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class APIconsts {
    private static final String SEARCH_API = "https://imdb8.p.rapidapi.com/auto-complete?q=";
    private static final String HEADER_KEY = "X-RapidAPI-Key";
    private static final String HEADER_KEY_VALUE = "48b0fa711dmsh44b45238372593ep142911jsn4c777e50417f";
    private static final String HEADER_HOST = "X-RapidAPI-Host";
    private static final String HEADER_HOST_VALUE = "imdb8.p.rapidapi.com";

    public static String getSearchAPI(String movieName) {
        movieName = movieName.replace(" ", "%20");
        return SEARCH_API + movieName;
    }

    public static List<String> getKeyAndValue() {
        List<String> keyAndValue = new ArrayList<>();
        keyAndValue.add(HEADER_KEY);
        keyAndValue.add(HEADER_KEY_VALUE);
        return keyAndValue;
    }

    public static List<String> getHostAndValue() {
        List<String> hostAndValue = new ArrayList<>();
        hostAndValue.add(HEADER_HOST);
        hostAndValue.add(HEADER_HOST_VALUE);
        return hostAndValue;
    }
}
