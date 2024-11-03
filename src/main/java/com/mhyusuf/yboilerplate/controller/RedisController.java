package com.mhyusuf.yboilerplate.controller;

import com.mhyusuf.yboilerplate.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // Get all keys
    @GetMapping("/all")
    public ResponseEntity<Set<String>> getAllKeys() {
        Set<String> keys = redisService.getAllKeys();
        return new ResponseEntity<>(keys, HttpStatus.OK);
    }


    // Create or Update data
    @PostMapping("/{key}")
    public ResponseEntity<String> createOrUpdateData(@PathVariable String key, @RequestBody String value) {
        redisService.saveData(key, value);
        return new ResponseEntity<>("Data saved successfully", HttpStatus.OK);
    }

    // Read data
    @GetMapping("/{key}")
    public ResponseEntity<String> getData(@PathVariable String key) {
        String value = redisService.getData(key);
        if (value != null) {
            return new ResponseEntity<>(value, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete data
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteData(@PathVariable String key) {
        boolean deleted = redisService.deleteData(key);
        if (deleted) {
            return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }
}
