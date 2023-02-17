package org.purplerat.httpConnetion;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPConnection implements Closeable {
    private final HttpURLConnection httpURLConnection;
    HTTPConnection(URL url, HTTPRequest.RequestFunc requestFunc) throws IOException{
        httpURLConnection = (HttpURLConnection) url.openConnection();
        requestFunc.run(httpURLConnection);
        httpURLConnection.connect();
    }
    public InputStream getInputStream()throws IOException {
        return httpURLConnection.getInputStream();
    }
    public InputStream getErrorStream() {
        return httpURLConnection.getErrorStream();
    }
    public int getResponseCode() throws IOException{
        return httpURLConnection.getResponseCode();
    }
    public String getResponseMessage() throws IOException{
        return httpURLConnection.getResponseMessage();
    }
    public String getCookie(){
        return httpURLConnection.getHeaderField("Set-Cookie");
    }
    public long getContentLength(){
        return httpURLConnection.getContentLength();
    }

    @Override
    public void close() {
        if(httpURLConnection!=null)httpURLConnection.disconnect();
    }
}
