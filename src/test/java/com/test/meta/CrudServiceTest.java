package com.test.meta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.meta.model.MetaDocument;
import com.test.meta.model.MetaResponse;
import com.test.meta.service.FakeCosmosService;
import com.test.meta.service.CrudService;
import com.test.meta.util.TestDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CrudServiceTest {

    private FakeCosmosService mockCosmosService;

    private CrudService crudServiceUnderTest;

    public CrudServiceTest() {
        mockCosmosService = mock(FakeCosmosService.class);
        crudServiceUnderTest = new CrudService(mockCosmosService);
    }

    @Test
    public void testRead_Query() {
        String stubDocId = TestDataUtil.FAKER.number().digits(6);

        List<MetaDocument> stubResponseList = TestDataUtil.getMetaDocumentsStub(List.of(stubDocId));

        Optional<String> incomingParameter = Optional.ofNullable(stubDocId);

        when(mockCosmosService.readItems()).thenReturn(stubResponseList);

        List<MetaDocument> readResponse = crudServiceUnderTest.read(incomingParameter);

        verify(mockCosmosService, times(1)).readItems();
        assertEquals(readResponse.size(), 1, "Test expects a list with 1 document.");
    }

    @Test
    public void testCreate() {
        String stubDocId = TestDataUtil.FAKER.number().digits(6);
        List<String> ids = Arrays.asList(stubDocId);
        List<MetaDocument> stubDocumentList = TestDataUtil.getMetaDocumentsStub(ids);

        when(mockCosmosService.createItem(any(MetaDocument.class)))
                .thenReturn(stubDocumentList.stream().findFirst().get());

        MetaResponse readResponse = null;
        try {
            readResponse = crudServiceUnderTest.createMultiple(Optional.of(CrudService.objectMapper.writeValueAsString(stubDocumentList)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        verify(mockCosmosService, times(1)).createItem(any(MetaDocument.class));
        assertEquals(1, readResponse.getResults().size(), "Test expects a list with 1 document.");
    }

}
