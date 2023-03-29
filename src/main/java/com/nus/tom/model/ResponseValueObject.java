package com.nus.tom.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ResponseValueObject implements Serializable {

   private String id;
   private String message;
   private ErrorObject errors;
}
