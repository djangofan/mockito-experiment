package com.test.meta.stubs;

import com.microsoft.azure.functions.*;
import lombok.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class HttpRequestMessageStub implements HttpRequestMessage<Optional<String>> {

    private URI uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private Optional<String> body;

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatus httpStatus) {
        return new HttpResponseMessage.Builder() {
            @Override
            public HttpResponseMessage.Builder status(HttpStatusType httpStatusType) {
                this.status(httpStatusType);
                return this;
            }

            @Override
            public HttpResponseMessage.Builder header(String s, String s1) {
                this.header(s, s1);
                return this;
            }

            @Override
            public HttpResponseMessage.Builder body(Object o) {
                this.body(o);
                return this;
            }

            @Override
            public HttpResponseMessage build() {
                return this.build();
            }
        }.status(httpStatus);
    }

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatusType httpStatusType) {
        return new HttpResponseMessage.Builder() {
            @Override
            public HttpResponseMessage.Builder status(HttpStatusType httpStatusType) {
                this.status(httpStatusType);
                return this;
            }

            @Override
            public HttpResponseMessage.Builder header(String s, String s1) {
                this.header(s, s1);
                return this;
            }

            @Override
            public HttpResponseMessage.Builder body(Object o) {
                this.body(o);
                return this;
            }

            @Override
            public HttpResponseMessage build() {
                return this.build();
            }
        }.status(httpStatusType);
    }

}
