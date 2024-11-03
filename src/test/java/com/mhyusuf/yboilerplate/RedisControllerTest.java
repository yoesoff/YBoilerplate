package com.mhyusuf.yboilerplate;

import com.mhyusuf.yboilerplate.controller.RedisController;
import com.mhyusuf.yboilerplate.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RedisController.class)
class RedisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisService redisService; // Gunakan @MockBean untuk RedisService

    @InjectMocks
    private RedisController redisController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateData() throws Exception {
        String key = "testKey";
        String value = "testValue";

        doNothing().when(redisService).saveData(key, value);

        mockMvc.perform(post("/api/redis/{key}", key)
                        .content(value)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Data saved successfully"));
    }

    @Test
    void testGetData() throws Exception {
        String key = "testKey";
        String value = "testValue";

        when(redisService.getData(key)).thenReturn(value);

        mockMvc.perform(get("/api/redis/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    void testGetData_NotFound() throws Exception {
        String key = "unknownKey";

        when(redisService.getData(key)).thenReturn(null);

        mockMvc.perform(get("/api/redis/{key}", key))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Data not found"));
    }

    @Test
    void testDeleteData() throws Exception {
        String key = "testKey";

        when(redisService.deleteData(key)).thenReturn(true);

        mockMvc.perform(delete("/api/redis/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string("Data deleted successfully"));
    }

    @Test
    void testDeleteData_NotFound() throws Exception {
        String key = "unknownKey";

        when(redisService.deleteData(key)).thenReturn(false);

        mockMvc.perform(delete("/api/redis/{key}", key))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Data not found"));
    }

    @Test
    void testGetAllKeys() throws Exception {
        Set<String> keys = Collections.singleton("testKey");

        when(redisService.getAllKeys()).thenReturn(keys);

        mockMvc.perform(get("/api/redis/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("testKey"));
    }
}
