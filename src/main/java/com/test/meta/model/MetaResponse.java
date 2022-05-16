package com.test.meta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.functions.HttpStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Jacksonized
@NoArgsConstructor
public class MetaResponse extends MetaError {

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("results")
    List<MetaDocument> results;

}
