package com.nus.tom.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ResponseValueObject implements Serializable {

   private String payload;
   private String message;
   private ErrorObject errors;
}
