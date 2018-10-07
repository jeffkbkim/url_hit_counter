### How to Run (java 1.8)
1. Go to file directory that contains `GenerateReport.java` and `input.txt`
2. run `javac GenerateReport.java`
3. run `java GenerateReport input.txt`


### Implementation Basic

1. Categorize each line of text according to date (day)
2. Categorize each date according to url
3. Sort the dates, and for each date:
- Sort the url and count (based on count) for each date

### Assumptions
1. number of unique urls >>> number of unique hits/dates
2. text file contains lines of text of the format `timestamp|url` (invalid input will error)

### Data Structures

    1. Map<Date, Map<String, URLCount>> map

        map {
            key: timestamp (day)
            value: map {
                key: url
                value: {url, count}
            }
        }

    2. URLCount class holds basic url information (url and count)
        String url
        int count

### Complexity Analysis  
- let `n` denote number of urls  
- let `k` denote number of days  
- Assumption: `n` >> `k` (assume `k` is constant when compared with `n`)  
  - **Space**: `k` maps and `n` urls for each map
    - `O(kn)` --> `O(n)`
  - **Runtime**: sort list of `k` dates, and sort list of `n` urls for each date
    - `O(klog(k)) + O(k * nlog(n))` --> `O(nlog(n))`
    

