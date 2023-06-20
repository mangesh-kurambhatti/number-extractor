package com.mk.parser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.parser.model.InputMessage;
import com.mk.parser.model.ParsedData;
import com.mk.parser.service.IMessageParserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = NumberExtractorController.class)
public class NumberExtractorControllerTest {

    @MockBean
    private IMessageParserService iMessageParserService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testReceiveIncomingMsg() throws Exception {
        InputMessage inputMessage=constructInputMessage();
        Mockito.when(iMessageParserService.parseText(inputMessage.getText())).thenReturn(getParsedData());

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/data/incomingMsg")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputMessage));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is("04a63473-b370-4026-9af8-dbc576cebd87")));
    }

    private InputMessage constructInputMessage() {
        String sampleText = "Take immediate action to stop the violation and notify King County Industrial Waste within 24 hours of learning of the violation. In case of violation penalty of $1,000,000 should be paid within 3 months.";

        InputMessage inputMessage= new InputMessage();
        inputMessage.setId("04a63473-b370-4026-9af8-dbc576cebd87");
        inputMessage.setText(sampleText);
        return inputMessage;
    }

    public List<ParsedData> getParsedData(){
        List<ParsedData> dataList= new ArrayList<>();

        dataList.add(new ParsedData("24",24,91,93));
        dataList.add(new ParsedData("1,000,000",1000000,163,172));
        dataList.add(new ParsedData("3",3,196,197));
        return dataList;
    }

}
//        DocumentRequest request = new DocumentRequest();
//        request.setId("04a63473-b370-4026-9af8-dbc576cebd87");
//        request.setText(sampleText);



        /*
        ResponseEntity<List<OutputMessage>> response = numberExtractionController.receiveIncomingMsg(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<ExtractedNumber> extractedNumbers = response.getBody();
        assertNotNull(extractedNumbers);
        assertEquals(3, extractedNumbers.size());

        ExtractedNumber extractedNumber1 = extractedNumbers.get(0);
        assertEquals("24", extractedNumber1.getExtractedText());
        assertEquals(24, extractedNumber1.getExtractedValue());
        assertEquals(91, extractedNumber1.getStartPosition());
        assertEquals(93, extractedNumber1.getEndPosition());

        ExtractedNumber extractedNumber2 = extractedNumbers.get(1);
        assertEquals("1,000,000", extractedNumber2.getExtractedText());
        assertEquals(1000000, extractedNumber2.getExtractedValue());
        assertEquals(163, extractedNumber2.getStartPosition());
        assertEquals(172, extractedNumber2.getEndPosition());

        ExtractedNumber extractedNumber3 = extractedNumbers.get(2);
        assertEquals("3", extractedNumber3.getExtractedText());
        assertEquals(3, extractedNumber3.getExtractedValue());
        assertEquals(196, extractedNumber3.getStartPosition());
        assertEquals(197, extractedNumber3.getEndPosition());
    }*/