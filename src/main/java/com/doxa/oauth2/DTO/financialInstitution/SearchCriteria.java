package com.doxa.oauth2.DTO.financialInstitution;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchCriteria {
        private String key;
        private String operation;
        private Object value;
}
