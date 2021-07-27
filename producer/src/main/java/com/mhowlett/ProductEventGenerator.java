// kind of stolen from: https://github.com/gwenshap/kafka-examples/blob/master/AvroProducerExample/src/main/java/com/shapira/examples/producer/avroclicks/EventGenerator.java
// under the assumption that she won't mind.

package com.mhowlett;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.redshelf.grpc.product.ProductOuterClass;
import com.redshelf.grpc.product.ProductOuterClass.Product;
import com.redshelf.grpc.university.UniversityOuterClass;

public class ProductEventGenerator {
    static long numUsers = 10000;
    static long currUser = 1;
    static String[] websites = {"support.html","about.html","foo.html", "bar.html", "home.html", "search.html", "list.html", "help.html", "bar.html", "foo.html"};

    public static Product getNext(Random r) {
        int ip4 = (int) currUser % 256;
        long runtime = new Date().getTime();


        Product.Builder product = Product.newBuilder();
        product.setId(websites[r.nextInt(websites.length)]);
        product.setName("Becoming");
        product.setType("Book");
        product.setDescription("Book by Michelle Obama");

        currUser += 1;
        return product.build();
    }
}
