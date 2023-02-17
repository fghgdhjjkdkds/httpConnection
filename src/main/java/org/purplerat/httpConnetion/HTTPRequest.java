package org.purplerat.httpConnetion;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequest {
    private final RequestFunc func;
    private HTTPRequest(RequestFunc func){
        this.func = func;
    }
    public static HTTPRequest create(){
        return new HTTPRequest(c->{});
    }
    public static HTTPRequest getDefaultRequest(){
        return create().setUserAgent(RandomUserAgent.getRandomAgent());
    }

    public HTTPConnection connect(URL url)throws IOException {
        return new HTTPConnection(url,func);
    }
    public HTTPRequest setCookie(String cookie){
        return template((conn)->conn.setRequestProperty("Cookie",cookie));
    }
    public HTTPRequest setReferer(String referer){
        return template((conn)->conn.setRequestProperty("Referer",referer));
    }
    public HTTPRequest setDoOutput(boolean output){
        return template((conn)->conn.setDoOutput(output));
    }
    public HTTPRequest setDoInput(boolean input){
        return template((conn)->conn.setDoInput(input));
    }
    public HTTPRequest setContentLength(int contentLength){
        return template((conn)->conn.setRequestProperty("Content-Length", Integer.toString(contentLength)));
    }
    public HTTPRequest setContentLength(String contentLength){
        return template((conn)->conn.setRequestProperty("Content-Length", contentLength));
    }
    public HTTPRequest setCharset(String charset){
        return template((conn)->conn.setRequestProperty("Charset", charset));
    }
    public HTTPRequest setContentType(String contentType){
        return template((conn)->conn.setRequestProperty("Content-Type", contentType));
    }
    public HTTPRequest setConnectTimeout(int timeout){
        return template((conn)->conn.setConnectTimeout(timeout));
    }
    public HTTPRequest setReadTimeout(int timeout){
        return template((conn)->conn.setReadTimeout(timeout));
    }
    public HTTPRequest setRange(long start,long end){
        return template((conn)->conn.setRequestProperty("Range",String.format("bytes=%s-%s",start,end)));
    }
    public HTTPRequest writePostData(byte[] postData){
        return template((conn)-> conn.getOutputStream().write(postData));
    }
    public HTTPRequest setUserAgent(String userAgent){
        return template((conn)->conn.setRequestProperty("User-Agent",userAgent));
    }
    public HTTPRequest setRequestMethod(String method){
        return template((conn)->conn.setRequestMethod(method));
    }
    public HTTPRequest setAccept(String accept){
        return template((conn)->conn.setRequestProperty("Accept",accept));
    }
    interface RequestFunc {
        void run(HttpURLConnection httpURLConnection) throws IOException;
    }
    private HTTPRequest template(RequestFunc requestFunc){
        return new HTTPRequest(conn -> {
            func.run(conn);
            requestFunc.run(conn);
        });
    }
}
