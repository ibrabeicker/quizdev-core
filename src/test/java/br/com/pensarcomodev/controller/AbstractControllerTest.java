package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.util.ParamMap;
import io.micronaut.core.type.Argument;
import io.micronaut.core.type.GenericArgument;
import io.micronaut.data.model.Page;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpParameters;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;

import java.util.Map;

public class AbstractControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    public <T> T get(String uri, Class<T> type) {
        HttpRequest<T> request = HttpRequest.GET(uri);
        return client.toBlocking().retrieve(request, type);
    }

    public <B, R> R post(String uri, B body, Class<R> type) {
        HttpRequest<B> request = HttpRequest.POST(uri, body);
        return client.toBlocking().retrieve(request, type);
    }

    public <T> Page<T> getWithPage(String uri, ParamMap parameters, Class<T> type) {
        MutableHttpRequest<T> request = HttpRequest.<T>GET(uri);
        parameters.getMap().forEach((k, v) -> request.getParameters().add(k, v.toString()));
        Argument<Page<T>> arg = new GenericArgument<>() {
        };
        return client.toBlocking().retrieve(request, arg);
    }

    public <T> Page<T> getWithPage(String uri, int page, int size, Class<T> type) {
        MutableHttpRequest<T> request = HttpRequest.<T>GET(uri);
        request.getParameters()
                .add("page", Integer.toString(page))
                .add("size", Integer.toString(size));
        return client.toBlocking().retrieve(request, Page.class);
    }

    public <T> Page<T> getWithPage(String uri, Class<T> type) {
        MutableHttpRequest<T> request = HttpRequest.<T>GET(uri);
        return client.toBlocking().retrieve(request, Page.class);
    }

}
