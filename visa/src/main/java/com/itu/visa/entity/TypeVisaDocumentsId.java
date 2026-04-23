package com.itu.visa.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeVisaDocumentsId implements Serializable {
    private Long typeVisa;
    private Long documentsType;
}
