package com.mk.parser.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OutputMessage {

    private String id;
    private List<ParsedData> dataList;

    public OutputMessage(String id, List<ParsedData> messageList) {
        this.id=id;
        this.dataList=messageList;
    }
}
