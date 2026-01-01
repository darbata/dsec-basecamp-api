package com.dsec.collab.adaptor.http;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

public interface ILogger {
    void logRequest(HttpRequest request, byte[] body);
    ClientHttpResponse logResponse(ClientHttpResponse response) throws IOException;
}