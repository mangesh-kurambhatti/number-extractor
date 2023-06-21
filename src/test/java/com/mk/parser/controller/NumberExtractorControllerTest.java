package com.mk.parser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.parser.model.InputMessage;
import com.mk.parser.model.ParsedData;
import com.mk.parser.service.IMessageParserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = NumberExtractorController.class)
public class NumberExtractorControllerTest {

    @InjectMocks
    private NumberExtractorController numberExtractorController;
    @MockBean
    private IMessageParserService iMessageParserService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testReceiveIncomingMsg1() throws Exception {
        InputMessage inputMessage=constructInputMessage();
        when(iMessageParserService.parseTextWithRegx(inputMessage.getText())).thenReturn(getParsedData());

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/data/incomingMsg1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputMessage));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is("04a63473-b370-4026-9af8-dbc576cebd87")));
    }

    @Test
    public void testReceiveIncomingMsgException() throws Exception{
        InputMessage inputMessage=constructInputMessage();
        //Mockito.doThrow(Exception.class).when(iMessageParserService).parseTextWithRegx(inputMessage.getText());

        when(iMessageParserService.parseTextWithRegx(inputMessage.getText())).then(invocation -> {
            throw new Exception();
        });

        ResponseEntity<?> responseEntity = numberExtractorController.receiveIncomingMsg1(inputMessage);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Something went wrong..!", responseEntity.getBody());
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

        dataList.add(new ParsedData("24",new BigDecimal("24"),91,93));
        dataList.add(new ParsedData("1,000,000",new BigDecimal("1000000"),163,172));
        dataList.add(new ParsedData("3",new BigDecimal("3"),196,197));
        return dataList;
    }

}