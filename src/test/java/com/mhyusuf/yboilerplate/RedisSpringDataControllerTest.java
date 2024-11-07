package com.mhyusuf.yboilerplate;

import com.mhyusuf.yboilerplate.controller.RedisSpringDataController;
import com.mhyusuf.yboilerplate.entity.DataObject;
import com.mhyusuf.yboilerplate.service.RedisSpringDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(RedisSpringDataController.class)
class RedisSpringDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisSpringDataService redisService; // Menggunakan @MockBean untuk memock RedisService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateData() throws Exception {
        String key = "testKey";
        String value = "testValue";

        doNothing().when(redisService).saveData(key, value);

        mockMvc.perform(post("/api/redisspringdata/{key}", key)
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

        mockMvc.perform(get("/api/redisspringdata/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    void testGetData_NotFound() throws Exception {
        String key = "unknownKey";

        when(redisService.getData(key)).thenReturn(null);

        mockMvc.perform(get("/api/redisspringdata/{key}", key))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Data not found"));
    }

    @Test
    void testDeleteData() throws Exception {
        String key = "testKey";

        when(redisService.deleteData(key)).thenReturn(true);

        mockMvc.perform(delete("/api/redisspringdata/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string("Data deleted successfully"));
    }

    @Test
    void testDeleteData_NotFound() throws Exception {
        String key = "unknownKey";

        when(redisService.deleteData(key)).thenReturn(false);

        mockMvc.perform(delete("/api/redisspringdata/{key}", key))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Data not found"));
    }

    @Test
    void testGetAllData() throws Exception {
        // Buat objek DataObject dengan key dan value
        DataObject dataObject = new DataObject("testKey", "testValue");
        Set<DataObject> dataObjects = Collections.singleton(dataObject);

        // Mock service untuk mengembalikan dataObjects
        when(redisService.getAllData()).thenReturn(dataObjects);

        // Lakukan perform GET request dan validasi respons JSON
        mockMvc.perform(get("/api/redisspringdata/all"))
                .andExpect(status().isOk());

    }
}
