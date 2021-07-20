package com.gmcc.aliface.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AliRegistor {
    private String name;
    private String userType;
    private String certType;
    private String certNo;
    private String uniqueId;
}
