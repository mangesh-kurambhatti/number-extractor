package com.mk.parser.controller;

import com.mk.parser.exception.BusinessException;
import com.mk.parser.model.InputMessage;
import com.mk.parser.model.OutputMessage;
import com.mk.parser.model.ParsedData;
import com.mk.parser.service.IMessageParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@Slf4j
public class NumberExtractorController {

    @Autowired
    private IMessageParserService parserService;

    /*Below method accept the Input Message with the required format and return the object with desired output.
      @param - inputMessage - represents the input object      */
    @PostMapping("/regxParsing")
    public ResponseEntity<?> receiveIncomingMsgForRegxParsing(@RequestBody InputMessage inputMessage) {
        List<ParsedData> messageList = null;
        OutputMessage message = null;
        try {
            if (inputMessage != null) {
                log.info("Message is : {}", inputMessage);
                messageList = parserService.parseTextWithRegx(inputMessage.getText());
            }
            if (messageList != null)
                message = new OutputMessage(inputMessage.getId(), messageList);
        }catch (Exception e){
            throw new BusinessException("Something Went Wrong..!");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /*Below method accept the Input Message with the required format and return the object with desired output.
    @param - inputMessage - represents the input object      */
    @PostMapping("/normalParsing")
    public ResponseEntity<?> receiveIncomingMsgForWithoutRegxParsing(@RequestBody InputMessage inputMessage) {
        List<ParsedData> messageList = null;
        OutputMessage message = null;
        try {
            if (inputMessage != null) {
                log.info("Message is : {}", inputMessage);
                messageList = parserService.parseDataWithoutRegx(inputMessage.getText());
            }
            if (messageList != null)
                message = new OutputMessage(inputMessage.getId(), messageList);
        }catch (Exception e){
            throw new BusinessException("Something Went Wrong..!");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
