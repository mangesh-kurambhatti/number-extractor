package com.mk.parser.service.impl;

import com.mk.parser.model.ParsedData;
import com.mk.parser.service.IMessageParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MessageParserServiceImpl implements IMessageParserService {

    /*Below method accept the string message & returns the List of parsed objects
     To match the pattern we have used the regex which identifies the matched pattern and provide us the respective start  & end index.
     @param - text - input text to be parsed      */
    @Override
    public List<ParsedData> parseText(String text) {

        List<ParsedData> messageList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d[\\d,]*(?:\\.\\d+)?");

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String extractedText = matcher.group();
            int startPosition = matcher.start();
            int endPosition = matcher.end();
            String valueWithoutCommas = extractedText.replace(",", "");
            try {
                long extractedValue = Long.parseLong(valueWithoutCommas);
                messageList.add(new ParsedData(extractedText, extractedValue, startPosition, endPosition));
            } catch (NumberFormatException e) {
                log.error("Please pass the correct value : {}", e.getMessage());
            } catch (Exception e) {
                log.error("Something went wrong : {}", e.getMessage());
            }
        }
        return messageList;
    }
}
