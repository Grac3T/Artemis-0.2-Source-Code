/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HTTPRequest {
    public URL url;
    private int timeout = 30000;
    private String cookie;
    private String referer;
    private String postData;
    private String useragent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private Proxy proxy;
    private boolean setFollowRedirects = true;
    private BufferedReader reader;
    private DataOutputStream writer;
    private HttpURLConnection connection;
    private Set<Map.Entry<String, List<String>>> lastConnectionHeaders;

    public HTTPRequest(URL url) {
        this.url = url;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public Set<Map.Entry<String, List<String>>> getLastConnectionHeaders() {
        return this.lastConnectionHeaders;
    }

    public void setFollowRedirects(boolean setFollowRedirects) {
        this.setFollowRedirects = setFollowRedirects;
    }

    private void setup() throws Exception {
        this.connection = this.proxy != null ? (HttpURLConnection)this.url.openConnection(this.proxy) : (HttpURLConnection)this.url.openConnection();
        if (this.cookie != null) {
            this.connection.setRequestProperty("Cookie", this.cookie);
        }
        if (this.referer != null) {
            this.connection.addRequestProperty("Referer", this.referer);
        }
        this.connection.setRequestProperty("User-Agent", this.useragent);
        this.connection.setReadTimeout(this.timeout);
        this.connection.setConnectTimeout(this.timeout);
        this.connection.setUseCaches(false);
        HttpURLConnection.setFollowRedirects(this.setFollowRedirects);
        if (this.postData != null) {
            this.connection.setRequestMethod("POST");
            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.writer = new DataOutputStream(this.connection.getOutputStream());
            this.writer.writeBytes(this.postData);
            this.writer.flush();
        }
        this.reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
    }

    public String[] read() throws Exception {
        ArrayList<String> st;
        try {
            String s;
            this.setup();
            st = new ArrayList<String>();
            while ((s = this.reader.readLine()) != null) {
                st.add(s);
            }
            this.lastConnectionHeaders = this.connection.getHeaderFields().entrySet();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            this.cleanup();
        }
        return st.toArray(new String[st.size()]);
    }

    public String[] read(int linesToRead) throws Exception {
        ArrayList<String> st;
        try {
            this.setup();
            st = new ArrayList<String>();
            for (int i = 0; i < linesToRead; ++i) {
                String s = this.reader.readLine();
                if (s == null) continue;
                st.add(s);
            }
            this.lastConnectionHeaders = this.connection.getHeaderFields().entrySet();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            this.cleanup();
        }
        return st.toArray(new String[st.size()]);
    }

    public String readSingle() throws Exception {
        String s;
        try {
            this.setup();
            s = this.reader.readLine();
            this.lastConnectionHeaders = this.connection.getHeaderFields().entrySet();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            this.cleanup();
        }
        return s;
    }

    public String readSingle(int linesToRead) throws Exception {
        String s;
        try {
            this.setup();
            for (int i = 0; i < linesToRead - 1; ++i) {
                this.reader.readLine();
            }
            s = this.reader.readLine();
            this.lastConnectionHeaders = this.connection.getHeaderFields().entrySet();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            this.cleanup();
        }
        return s;
    }

    private void cleanup() {
        try {
            this.reader.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.writer.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.connection.disconnect();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.reader = null;
        this.writer = null;
        this.connection = null;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

