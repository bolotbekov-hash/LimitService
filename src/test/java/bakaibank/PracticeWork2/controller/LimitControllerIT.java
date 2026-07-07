package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.service.LimitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LimitController.class)
class LimitControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Используем актуальную аннотацию для Spring Boot 3.4
    private LimitService limitService;

    private Limit sampleLimit;

    @BeforeEach
    void setUp() {
        sampleLimit = new Limit();
        sampleLimit.setId(1L);
        sampleLimit.setType("CASH");
        sampleLimit.setDefaultSum(new BigDecimal("10000.00"));
        sampleLimit.setDefaultOperationCount(5);
        sampleLimit.setMaxSum(new BigDecimal("50000.00"));
        sampleLimit.setMaxOperationCount(10);
    }

    // 1. ТЗ: Получение лимита (GET /api/v1/limits/active)
    @Test
    void getLimitsEndpointShouldReturnOk() throws Exception {
        when(limitService.getAllActiveLimits()).thenReturn(List.of(sampleLimit));

        mockMvc.perform(get("/api/v1/limits/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].type").value("CASH"))
                .andExpect(jsonPath("$[0].defaultSum").value(10000.00));
    }

    // 2. ТЗ: Успешное изменение лимита (PUT /api/v1/limits/{id})
    @Test
    void updateLimitEndpointShouldReturnUpdatedLimit() throws Exception {
        Limit updatedLimit = new Limit();
        updatedLimit.setId(1L);
        updatedLimit.setType("CASH");
        updatedLimit.setDefaultSum(new BigDecimal("20000.00"));

        when(limitService.updateLimit(eq(1L), any(Limit.class))).thenReturn(updatedLimit);

        String requestBody = "{"
                + "\"type\":\"CASH\","
                + "\"defaultSum\":20000.00,"
                + "\"defaultOperationCount\":5,"
                + "\"maxSum\":50000.00,"
                + "\"maxOperationCount\":10"
                + "}";

        mockMvc.perform(put("/api/v1/limits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.defaultSum").value(20000.00));
    }

    // 3. ТЗ: Успешное создание лимита (POST /api/v1/limits)
    @Test
    void createLimitEndpointShouldReturnCreatedStatus() throws Exception {
        when(limitService.createLimit(any(Limit.class))).thenReturn(sampleLimit);

        String requestBody = "{"
                + "\"type\":\"CASH\","
                + "\"defaultSum\":10000.00,"
                + "\"defaultOperationCount\":5,"
                + "\"maxSum\":50000.00,"
                + "\"maxOperationCount\":10"
                + "}";

        mockMvc.perform(post("/api/v1/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()) // Проверяет статус 201 Created
                .andExpect(jsonPath("$.type").value("CASH"));
    }
}