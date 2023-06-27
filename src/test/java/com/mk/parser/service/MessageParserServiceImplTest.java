package com.mk.parser.service;

import com.mk.parser.model.ParsedData;
import com.mk.parser.service.impl.MessageParserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MessageParserServiceImplTest {

    @InjectMocks
    private MessageParserServiceImpl service;

    String sampleText = "Take immediate action to stop the violation and notify King County Industrial Waste within 24 hours of learning of the violation. In case of violation penalty of $1,000,000 should be paid within 3 months.";

    /*Added Equals method in @ParsedData class to compare object
    * using that evaluated a test case for all data like earlier we were comparing one instance at a time
    * */
    @Test
    public void testParseText() {
        List<ParsedData> parsedDataList = getParsedData();
        List<ParsedData> list = service.parseTextWithRegx(sampleText);
        for(int i=0;i<list.size()-1;i++){
            assertTrue(parsedDataList.get(i).equals(list.get(i)));
        }
//        assertEquals("24", extractedNumber1.getExtractedText());
//        assertEquals(BigDecimal.valueOf(24), extractedNumber1.getExtractedValue());
//        assertEquals(91, extractedNumber1.getStartPosition());
//        assertEquals(93, extractedNumber1.getEndPosition());
    }

    public List<ParsedData> getParsedData() {
        List<ParsedData> dataList = new ArrayList<>();
        dataList.add(new ParsedData("24", new BigDecimal("24"), 91, 93));
        dataList.add(new ParsedData("1,000,000", new BigDecimal("1000000"), 163, 172));
        dataList.add(new ParsedData("3", new BigDecimal("3"), 196, 197));
        return dataList;
    }
}
