package com.mhyusuf.yboilerplate;

import com.mhyusuf.yboilerplate.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testSaveData() {
        String key = "testKey";
        String value = "testValue";

        redisService.saveData(key, value);

        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    void testGetData() {
        String key = "testKey";
        String value = "testValue";

        when(valueOperations.get(key)).thenReturn(value);

        String result = redisService.getData(key);

        assertEquals(value, result);
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    void testDeleteData() {
        String key = "testKey";

        when(redisTemplate.delete(key)).thenReturn(true);

        boolean result = redisService.deleteData(key);

        assertTrue(result);
        verify(redisTemplate, times(1)).delete(key);
    }

    @Test
    void testGetAllKeys() {
        Set<String> keys = Collections.singleton("testKey");

        when(redisTemplate.keys("*")).thenReturn(keys);

        Set<String> result = redisService.getAllKeys();

        assertEquals(keys, result);
        verify(redisTemplate, times(1)).keys("*");
    }
}
