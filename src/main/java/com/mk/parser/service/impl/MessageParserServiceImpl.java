package com.mk.parser.service.impl;

import com.mk.parser.model.ParsedData;
import com.mk.parser.service.IMessageParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MessageParserServiceImpl implements IMessageParserService {

    /*Below method accept the string message & returns the List of parsed objects
     To match the pattern we used the regex which identifies the matched pattern and provide us the respective start  & end indexes.
     @param - text - input text to be parsed      */
    @Override
    public List<ParsedData> parseTextWithRegx(String text) {

        List<ParsedData> messageList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d[\\d,]*(?:\\.\\d+)?");

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String extractedText = matcher.group();
            int startPosition = matcher.start();
            int endPosition = matcher.end();
            String valueWithoutCommas = extractedText.replace(",", "");
            try {
                messageList.add(new ParsedData(extractedText, new BigDecimal(valueWithoutCommas), startPosition, endPosition));
            } catch (NumberFormatException e) {
                log.error("Please pass the correct value : {}", e.getMessage());
            } catch (Exception e) {
                log.error("Something went wrong : {}", e.getMessage());
            }
        }
        return messageList;
    }


    /*Below method accept the string message & returns the List of parsed objects
     To match the pattern we traverse the text character by character and when we found a character as digit
     we mark it as start and we check further for more digits to mark it as end.
     @param - text - input text to be parsed
     */
    @Override
    public List<ParsedData> parseDataWithoutRegx(String text) {
        List<ParsedData> messageList = new ArrayList<>();
        int count = 0;
        int startIndex = 0;
        int endIndex = 0;
        try {
            for (int i = 0; i < text.length() - 1; i++) {
                StringBuilder builder = new StringBuilder();
                Boolean flag = Character.isDigit(text.charAt(i));
                if (flag) {
                    startIndex = i;
                    builder.append(text.charAt(i));
                    count++;
                    for (int j = i; j < text.length(); j++) {
                        Boolean isDigit = text.charAt(j + 1) == ',' || Character.isDigit(text.charAt(j + 1)) || text.charAt(j + 1) == '.';
                        if (isDigit) {
                            builder.append(text.charAt(j + 1));
                            count++;
                        } else {
                            endIndex = count + startIndex;
                            count = 0;
                            i = endIndex;
                            String numberWithoutComma = builder.toString().replace(",", "");
                            messageList.add(new ParsedData(builder.toString(), new BigDecimal(numberWithoutComma), startIndex, endIndex));
                            break;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            log.error("Number format is incorrect -: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Something went wrong while parsing text with message -: {}", e.getMessage());
        }
        return messageList;
    }
}
