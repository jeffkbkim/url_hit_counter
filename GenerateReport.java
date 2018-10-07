import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Please refer to README.md for implementation and time/space complexity analysis. 
 */
public class GenerateReport {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("must pass in file path.");
            return;
        }
        try {
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);
            parseFile(sc);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    // parses file line by lane and process as reading
    private static void parseFile(Scanner sc) {
        Map<Date, Map<String, URLCount>> map = new HashMap();
        while (sc.hasNextLine()) {
            // assume input format is the same (does not handle errors)
            String line = sc.nextLine();
            String[] split = line.split("\\|"); 
            processURL(map, Integer.valueOf(split[0]), split[1]);
        }
        printReport(map);
        sc.close();
    }

    // process given line of text and update map (increment counter for url in date)
    private static void processURL(Map<Date, Map<String, URLCount>> map, int timestamp, String url) {
        Date date = timestamp2Day(timestamp);
        Map<String, URLCount> urlCounts = map.getOrDefault(date, new HashMap());
        URLCount url_count = urlCounts.getOrDefault(url, new URLCount(url, 0));
        url_count.increment_count();
        urlCounts.put(url, url_count);
        map.put(date, urlCounts);
    }

    // sorts list of dates -> sort and print url+count for each date
    private static void printReport(Map<Date, Map<String, URLCount>> map) {
        ArrayList<Date> dateList = new ArrayList(map.keySet());
        Collections.sort(dateList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
        for (Date d : dateList) {
            System.out.println(dateFormat.format(d) + " GMT");
            printURLCount(map.get(d));
        }
    }

    // print url+count for a date
    private static void printURLCount(Map<String, URLCount> urlCounts) {
        ArrayList<URLCount> urlCountList = new ArrayList(urlCounts.values());
        Collections.sort(urlCountList, new URLCountComparator());
        for (URLCount urlCount : urlCountList) {
            System.out.println(urlCount.url + " " + urlCount.count);
        }
    }

    // convert timestamp to Date object without time (truncate hr, s, ms)
    private static Date timestamp2Day(int timestamp) {
        Date date = new Date((long) timestamp * 1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}

class URLCount {
    String url;
    int count;

    public URLCount(String url, int count) {
        this.url = url;
        this.count = count;
    }

    public void increment_count() {
        this.count++;
    }
}

// comparator to sort url+count object based on count
class URLCountComparator implements Comparator<URLCount> {
    public int compare(URLCount a, URLCount b) {
        if (a.count != b.count) return b.count - a.count;
        return a.url.compareTo(b.url);
    }
}