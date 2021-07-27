// kind of stolen from: https://github.com/gwenshap/kafka-examples/blob/master/AvroProducerExample/src/main/java/com/shapira/examples/producer/avroclicks/EventGenerator.java
// under the assumption that she won't mind.

package com.mhowlett;

import java.util.Date;
import java.util.Random;
import com.mhowlett.LogLineOuterClass.LogLine;

public class EventGenerator {
    static long numUsers = 10000;
    static long currUser = 1;

    static String[] websites = {"support.html","about.html","foo.html", "bar.html", "home.html", "search.html", "list.html", "help.html", "bar.html", "foo.html"};

    public static LogLine getNext(Random r) {
        int ip4 = (int) currUser % 256;
        long runtime = new Date().getTime();
        LogLine.Builder builder = LogLine.newBuilder();
        builder.setIp("66.249.1." + ip4);
        builder.setReferer("example.com");
        builder.setTimestamp(runtime);
        builder.setUrl(websites[r.nextInt(websites.length)]);
        builder.setUseragent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.125 Safari/537.36");
        currUser += 1;
        return builder.build();
    }
}
