package com.nus.tom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.tom.service.impl.LeaveRecommendationServiceImpl;
import com.nus.tom.util.LeavePolicyCache;
import com.nus.tom.util.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "classpath:application.yml")
public class LeaveRecommendationServiceTest {
    @InjectMocks
    private LeaveRecommendationServiceImpl leaveRecommendationService;

    @Mock
    private ResponseHelper responseHelper;
    @Mock
    private ObjectMapper objectMapper;


    private LeavePolicyCache leavePolicyCache;

    @BeforeEach
    void init() {

        leavePolicyCache = new LeavePolicyCache();
        ReflectionTestUtils.setField(leavePolicyCache, "mvelString", "project.priority < 3");
        leavePolicyCache.init();
    }

    @Test
    @DisplayName("test leave recommendation")
    public void testLeaveRecommendation() {

        boolean result = leaveRecommendationService.analyze("");

        Assertions.assertEquals(Boolean.TRUE, result);

    }
}
