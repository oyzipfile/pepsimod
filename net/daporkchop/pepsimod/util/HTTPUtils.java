// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.Charsets;
import java.io.IOException;
import org.apache.commons.lang3.Validate;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtils
{
    public static HttpURLConnection createUrlConnection(final URL url) throws IOException {
        Validate.notNull((Object)url);
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setUseCaches(false);
        return connection;
    }
    
    public static String performPostRequest(final URL url, final String post, final String contentType) throws IOException {
        Validate.notNull((Object)url);
        Validate.notNull((Object)post);
        Validate.notNull((Object)contentType);
        final HttpURLConnection connection = createUrlConnection(url);
        final byte[] postAsBytes = post.getBytes(Charsets.UTF_8);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", "" + postAsBytes.length);
        connection.setDoOutput(true);
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
            IOUtils.write(postAsBytes, outputStream);
        }
        finally {
            IOUtils.closeQuietly(outputStream);
        }
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            final String result = IOUtils.toString(inputStream, Charsets.UTF_8);
            return result;
        }
        catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream != null) {
                final String result2 = IOUtils.toString(inputStream, Charsets.UTF_8);
                return result2;
            }
            throw e;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public static String performPostRequestWithAuth(final URL url, final String post, final String contentType, final String auth) throws IOException {
        Validate.notNull((Object)url);
        Validate.notNull((Object)post);
        Validate.notNull((Object)contentType);
        final HttpURLConnection connection = createUrlConnection(url);
        final byte[] postAsBytes = post.getBytes(Charsets.UTF_8);
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", "" + postAsBytes.length);
        connection.setDoOutput(true);
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
            IOUtils.write(postAsBytes, outputStream);
        }
        finally {
            IOUtils.closeQuietly(outputStream);
        }
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            final String result = IOUtils.toString(inputStream, Charsets.UTF_8);
            return result;
        }
        catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream != null) {
                final String result2 = IOUtils.toString(inputStream, Charsets.UTF_8);
                return result2;
            }
            throw e;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public static String performGetRequest(final URL url) throws IOException {
        Validate.notNull((Object)url);
        final HttpURLConnection connection = createUrlConnection(url);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            final String result = IOUtils.toString(inputStream, Charsets.UTF_8);
            return result;
        }
        catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream != null) {
                final String result2 = IOUtils.toString(inputStream, Charsets.UTF_8);
                return result2;
            }
            throw e;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public static URL constantURL(final String url) {
        try {
            return new URL(url);
        }
        catch (MalformedURLException ex) {
            throw new Error("Couldn't create constant for " + url, ex);
        }
    }
    
    public static String buildQuery(final Map<String, Object> query) {
        if (query == null) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (final Map.Entry<String, Object> entry : query.entrySet()) {
            if (builder.length() > 0) {
                builder.append('&');
            }
            try {
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException ex) {}
            if (entry.getValue() != null) {
                builder.append('=');
                try {
                    builder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException ex2) {}
            }
        }
        return builder.toString();
    }
    
    public static URL concatenateURL(final URL url, final String query) {
        try {
            URL url2;
            if (url.getQuery() != null && !url.getQuery().isEmpty()) {
                final String protocol;
                final String host;
                final int port;
                final StringBuilder sb;
                url2 = new URL(protocol, host, port, sb.append(url.getFile()).append("&").append(query).toString());
                protocol = url.getProtocol();
                host = url.getHost();
                port = url.getPort();
                sb = new StringBuilder();
            }
            else {
                final String protocol2;
                final String host2;
                final int port2;
                final StringBuilder sb2;
                url2 = new URL(protocol2, host2, port2, sb2.append(url.getFile()).append("?").append(query).toString());
                protocol2 = url.getProtocol();
                host2 = url.getHost();
                port2 = url.getPort();
                sb2 = new StringBuilder();
            }
            return url2;
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Could not concatenate given URL with GET arguments!", ex);
        }
    }
}
