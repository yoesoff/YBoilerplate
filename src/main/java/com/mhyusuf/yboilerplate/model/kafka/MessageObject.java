package com.mhyusuf.yboilerplate.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageObject {
    private String id;
    private String content;
    private String sender;
    private long timestamp;
}
