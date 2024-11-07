package com.mhyusuf.yboilerplate.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("DataObject")
public class DataObject implements Serializable {
    @Id
    private String id;
    private String value;

    // Constructors, getters, and setters
    public DataObject() {}

    public DataObject(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
