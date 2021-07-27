package com.mhowlett;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

import static org.apache.kafka.common.utils.Utils.readFileAsString;

public class SendToSchemaDirectly {

    private final static MediaType SCHEMA_CONTENT =
            MediaType.parse("Content-Type:application/vnd.schemaregistry.v1+json");

    public static void main(String[] args) throws IOException {
        String text = readFileAsString("/Users/rahilhussain/redshelf/schema/srproto/producer/src/main/proto/LogLine.proto");

        JSONObject object = new JSONObject();
        object.put("schemaType", "PROTOBUF");
        object.put("schema", text);

        System.out.println(object.toString(1));

        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .post(RequestBody.create(SCHEMA_CONTENT, object.toString(2)))
                .url("http://localhost:8081/subjects/Mumbai/versions")
                .build();

        String output = client.newCall(request).execute().body().string();
        System.out.println(output);
    }
}
