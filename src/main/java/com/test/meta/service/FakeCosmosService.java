package com.test.meta.service;

import com.github.javafaker.Faker;
import com.test.meta.util.TestDataUtil;
import com.test.meta.model.MetaDocument;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FakeCosmosService {

    public FakeCosmosService() {
        log.info("Connect Cosmos");
    }

    public List<MetaDocument> readItems() {
        return new ArrayList<>(TestDataUtil.getMetaDocumentsStub(List.of(Faker.instance().number().digits(7))));
    }

    public MetaDocument createItem(MetaDocument dbFields) {
        //reflect it back
        return dbFields;
    }

    public Map<String, String> deleteItem(String id) {
        Map<String, String> fakeResult = new HashMap<>();
        fakeResult.put("result", "whatever");
        return fakeResult;
    }

    public MetaDocument updateItem(MetaDocument dbFields, String id) {
        return MetaDocument.builder().build();
    }

}

