package com.test.meta;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.test.meta.model.MetaDocument;
import com.test.meta.service.CrudService;
import com.test.meta.util.EnvUtil;
import com.test.meta.util.MetaException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HttpTriggerFunctions {

    private CrudService metaService;
    private static final String CODE_SUCCESS = EnvUtil.getProperty(EnvUtil.AppVar.CODE_SUCCESS);

    public HttpTriggerFunctions() {
        this.metaService = new CrudService();
    }

    @FunctionName("MetaRead")
    public HttpResponseMessage read(
            @HttpTrigger(
                    name = "r",
                    methods = {HttpMethod.GET},
                    route = "meta",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request)
            throws MetaException {
        return request.createResponseBuilder(HttpStatus.valueOf(Integer.parseInt(CODE_SUCCESS)))
                    .body(this.metaService.read(Optional.ofNullable(request.getQueryParameters().get(CrudService.TEST_INTEGER))))
                    .build();
    }

    @FunctionName("MetaCreate")
    public HttpResponseMessage create(
            @HttpTrigger(
                    name = "c",
                    methods = {HttpMethod.POST},
                    route = "meta",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request)
            throws MetaException {
        return request.createResponseBuilder(HttpStatus.valueOf(Integer.parseInt(CODE_SUCCESS)))
                .body(this.metaService.createMultiple(request.getBody()))
                .build();
    }

    @FunctionName("MetaCreateAlt")
    public HttpResponseMessage createAlt(
            @HttpTrigger(
                    name = "ca",
                    methods = {HttpMethod.POST},
                    route = "meta/alt",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<List<MetaDocument>>> request)
            throws MetaException {
        return request.createResponseBuilder(HttpStatus.valueOf(Integer.parseInt(CODE_SUCCESS)))
                .body(this.metaService.createMultipleAlt(request.getBody()))
                .build();
    }

    @FunctionName("MetaUpdate")
    public HttpResponseMessage update(
            @HttpTrigger(
                    name = "u",
                    methods = {HttpMethod.PUT},
                    route = "meta",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<String> request,
            final ExecutionContext context) throws MetaException {
        //TODO
        return this.metaService.update(request);
    }

    @FunctionName("MetaDelete")
    public HttpResponseMessage delete(
            @HttpTrigger(
                    name = "d",
                    methods = {HttpMethod.DELETE},
                    route = "meta",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<String> request,
            final ExecutionContext context) throws MetaException {
        //TODO
        return this.metaService.delete(request);
    }


}
