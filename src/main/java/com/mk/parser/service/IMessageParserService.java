package com.mk.parser.service;

import com.mk.parser.model.ParsedData;

import java.util.List;

public interface IMessageParserService {

    public List<ParsedData> parseTextWithRegx(String text);
    public List<ParsedData> parseDataWithoutRegx(String text);
}
