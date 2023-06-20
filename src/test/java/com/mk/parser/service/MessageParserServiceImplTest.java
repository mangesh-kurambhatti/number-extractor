package com.mk.parser.service;

import com.mk.parser.model.ParsedData;
import com.mk.parser.service.impl.MessageParserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageParserServiceImplTest {

    @InjectMocks
    private MessageParserServiceImpl service;

    String sampleText = "Take immediate action to stop the violation and notify King County Industrial Waste within 24 hours of learning of the violation. In case of violation penalty of $1,000,000 should be paid within 3 months.";

    @Test
    public void testParseText(){

//        Mockito.when(iMessageParserService.parseText("abc")).thenReturn(getParsedData())

        List<ParsedData> list= service.parseText(sampleText);

        ParsedData extractedNumber1 = list.get(0);
        assertEquals("24", extractedNumber1.getExtractedText());
        assertEquals(24, extractedNumber1.getExtractedValue());
        assertEquals(91, extractedNumber1.getStartPosition());
        assertEquals(93, extractedNumber1.getEndPosition());

    }
    public List<ParsedData> getParsedData(){
        List<ParsedData> dataList= new ArrayList<>();

        dataList.add(new ParsedData("24",24,91,93));
        dataList.add(new ParsedData("1,000,000",1000000,163,172));
        dataList.add(new ParsedData("3",3,196,197));
        return dataList;
    }
}
