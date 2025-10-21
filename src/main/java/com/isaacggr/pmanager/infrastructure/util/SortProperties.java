package com.isaacggr.pmanager.infrastructure.util;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;


@Getter
public class SortProperties {
    
    private static final List<String> ALLOWED_PROPERTIES = List.of(
        "title",
        "status",
        "numberOfDays"
    );

    private final List<String> sortPropertiesList;

    public SortProperties(String commaSeparatedString) {
        sortPropertiesList = Arrays
        .stream(commaSeparatedString.split(","))
        .filter(ALLOWED_PROPERTIES::contains)
        .toList();
    }
}
