package com.test.meta.util;

import com.github.javafaker.Faker;
import com.test.meta.model.MetaDocument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static final Faker FAKER = new Faker();

    public static List<MetaDocument> getMetaDocumentsStub(List<String> stubDocIds) {
        List<MetaDocument> responseList = new ArrayList<>();
        for (String item : stubDocIds) {
            MetaDocument metaDocument = MetaDocument.builder()
                    .testDate(LocalDate.now())
                    .testInteger(Integer.parseInt(item))
                    .testString(FAKER.chuckNorris().fact())
                    .testList(List.of("one", "two", "three"))
                    .build();
            responseList.add(metaDocument);
        }
        return responseList;
    }

}
