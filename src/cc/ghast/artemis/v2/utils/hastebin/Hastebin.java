/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package cc.ghast.artemis.v2.utils.hastebin;

import cc.ghast.artemis.v2.utils.http.HTTPRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

public class Hastebin {
    private static final String endpoint = "https://hasteb.in/documents";

    public static String paste(String[] s) throws IOException {
        String payload = "";
        for (String string : s) {
            payload = payload + "\n" + string;
        }
        HTTPRequest request = new HTTPRequest(new URL(endpoint));
        request.setTimeout(1000);
        request.setUseragent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");
        request.setPostData(payload);
        try {
            String[] res = request.read();
            String reply = res[0];
            JsonParser parser = new JsonParser();
            return "https://hasteb.in/" + parser.parse(reply).getAsJsonObject().get("key").getAsString();
        }
        catch (Exception e) {
            System.out.println("[!]---> Unhandled HTTP request exception");
            return null;
        }
    }
}

