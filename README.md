# number-extractor

This repository represents the web app to extract numbers from a given string with their position.


### Sample Input

```
{
"id": "04a63473-b370-4026-9af8-dbc576cebd87",
"text": "Take immediate action to stop the violation and notify King County Industrial Waste within 24
hours of learning of the violation. In case of violation penanly of $1,000,000 should be paid with in 3 months."
}
```

### Sample Output

```
{
    "id": "04a63473-b370-4026-9af8-dbc576cebd87",
    "dataList": [
        {
            "extractedText": "24",
            "extractedValue": 24,
            "startPosition": 91,
            "endPosition": 93
        },
        {
            "extractedText": "1,000,000",
            "extractedValue": 1000000,
            "startPosition": 163,
            "endPosition": 172
        },
        {
            "extractedText": "3",
            "extractedValue": 3,
            "startPosition": 196,
            "endPosition": 197
        }
    ]
}
```

### Code flow

1. **Controller-:**
   1. Here we have implemented a *receiveIncomingMsg()* which return the list of parsed text in specified format
