package com.mk.parser.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ParsedData {
    private String extractedText;
    private BigDecimal extractedValue;
    private int startPosition;
    private int endPosition;

    public ParsedData(String extractedText, BigDecimal extractedValue, int startPosition, int endPosition) {
        this.extractedText = extractedText;
        this.extractedValue = extractedValue;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
