package com.test.meta.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.test.meta.util.TestDataUtil;
import com.test.meta.model.MetaDocument;
import com.test.meta.model.MetaResponse;
import com.test.meta.util.MetaDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

@Slf4j
public class CrudService {

    public static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(
                    new JavaTimeModule().addDeserializer(LocalDate.class, new LocalDateDeserializer(MetaDateUtil.DATE_TIME_FORMATTER))
            )
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            .findAndRegisterModules();

    public FakeCosmosService cosmosService;

    public static final String TEST_INTEGER = "testInteger";

    public CrudService() {
        this(new FakeCosmosService());
    }

    public CrudService(FakeCosmosService testClient) {
        this.cosmosService = testClient;
    }

    public List<MetaDocument> read(Optional<String> queryId) {
        log.info("MetaService.read() ...");
        if (queryId.isPresent()) {
            return cosmosService.readItems();
        }
        return TestDataUtil.getMetaDocumentsStub(new ArrayList<>());
    }

    /**
     * Implementation of handling multiple item create.
     * @param request
     * @return
     */
    public MetaResponse createMultiple(Optional<String> request) {
        log.info("MetaService.create() ...");
        List<MetaDocument> results = new ArrayList<>();
        List<MetaDocument> requestList = new ArrayList<>();
        MetaResponse metaResponse = new MetaResponse();
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            if (request.isPresent()) {
                requestList = objectMapper.readValue(request.get(), typeFactory.constructCollectionType(List.class, MetaDocument.class));
            }
        } catch (JsonProcessingException e) {
            log.info("Parsing error on request.");
            metaResponse.setError(e.getMessage());
            metaResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
            return metaResponse;
        }
        for ( MetaDocument doc : requestList ) {
            results.add(this.create(doc));
        }
        metaResponse.setStatus(HttpStatus.OK);
        metaResponse.setResults(results);
        return metaResponse;
    }

    public MetaResponse createMultipleAlt(Optional<List<MetaDocument>> request) {
        log.info("MetaService.createMultipleAlt() ...");
        List<MetaDocument> results = new ArrayList<>();
        List<MetaDocument> requestList = request.orElse(new ArrayList<>());
        MetaResponse metaResponse = new MetaResponse();

        for ( MetaDocument doc : requestList ) {
            results.add(this.create(doc));
        }
        metaResponse.setStatus(HttpStatus.OK);
        metaResponse.setResults(results);
        return metaResponse;
    }

    /**
     * Split out as separate method because the interface only allows creating 1 item at a time
     *  and does not have a 'createItems' method.
     */
    public MetaDocument create(MetaDocument document) {
        log.info("MetaService.create() ...");
        Objects.requireNonNull(this.cosmosService, "Service must be instantiated.");
        document.setId(UUID.randomUUID());
        return cosmosService.createItem(document);
    }

    public HttpResponseMessage update(HttpRequestMessage<String> request) {
        return request.createResponseBuilder(HttpStatus.OK)
                .body("{}")
                .build();
    }

    public HttpResponseMessage delete(HttpRequestMessage<String> request) {
        return request.createResponseBuilder(HttpStatus.OK)
                .body("{}")
                .build();
    }

}
