package com.mk.parser.service;

import com.mk.parser.model.ParsedData;

import java.util.List;

public interface IMessageParserService {

    public List<ParsedData> parseText(String text);
}
