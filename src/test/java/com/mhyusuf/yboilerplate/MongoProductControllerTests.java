package com.mhyusuf.yboilerplate;

import com.mhyusuf.yboilerplate.controller.MongoProductController;
import com.mhyusuf.yboilerplate.service.MongoProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MongoProductController.class)
public class MongoProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }
}
