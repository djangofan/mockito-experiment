package com.test.meta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.test.meta.util.MetaDateUtil;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaDocument {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("testInteger")
    private Integer testInteger;

    @JsonProperty("testString")
    private String testString;

    @JsonProperty("testList")
    private List<String> testList;

    @JsonProperty("testDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = MetaDateUtil.pattern)
    private LocalDate testDate;

}
