package com.test.meta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Jacksonized
@NoArgsConstructor
public class MetaError {

    @JsonProperty("error")
    private String error;

}
