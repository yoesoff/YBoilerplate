package com.mhyusuf.yboilerplate.controller;

import com.mhyusuf.yboilerplate.entity.DataObject;
import com.mhyusuf.yboilerplate.service.RedisSpringDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redisspringdata")
public class RedisSpringDataController {

    private final RedisSpringDataService redisService;

    @Autowired
    public RedisSpringDataController(RedisSpringDataService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/{key}")
    public ResponseEntity<String> createOrUpdateData(@PathVariable String key, @RequestBody String value) {
        redisService.saveData(key, value);
        return ResponseEntity.ok("Data saved successfully");
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getData(@PathVariable String key) {
        String value = redisService.getData(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteData(@PathVariable String key) {
        if (redisService.deleteData(key)) {
            return ResponseEntity.ok("Data deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<DataObject>> getAllData() {
        return ResponseEntity.ok(redisService.getAllData());
    }
}
