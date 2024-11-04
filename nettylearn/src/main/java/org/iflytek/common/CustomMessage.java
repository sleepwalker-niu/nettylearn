package org.iflytek.common;

import lombok.Data;
import org.iflytek.model.CustomRequestBody;
@Data
public class CustomMessage {
    private CustomHeader header;
    private String body;
}
