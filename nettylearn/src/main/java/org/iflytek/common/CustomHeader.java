package org.iflytek.common;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

@Data
public class CustomHeader {
    private Long reqId;

    private Character reqType;

    private Integer reqLen;

}
