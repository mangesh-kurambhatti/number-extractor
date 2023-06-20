package com.mk.parser.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParsedData {
    private String extractedText;
    private long extractedValue;
    private int startPosition;
    private int endPosition;

    public ParsedData(String extractedText, long extractedValue, int startPosition, int endPosition) {
        this.extractedText = extractedText;
        this.extractedValue = extractedValue;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
