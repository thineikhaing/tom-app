package com.nus.tom.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.tom.model.LeavePolicy;
import com.nus.tom.model.LeaveRecommendation;
import com.nus.tom.model.Project;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.LeaveRecommendationService;
import com.nus.tom.util.LeavePolicyCache;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mvel2.MVEL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveRecommendationServiceImpl implements LeaveRecommendationService {
    private final ResponseHelper responseHelper;

    private final ObjectMapper objectMapper;

    /**
     * analyze using leave policy
     *
     * @param employeeId
     * @return
     */
    public boolean analyze(String employeeId) {

        Map<String, Object> context = new HashMap<>();

        //replace with employee's projects by leave request period
        context.put("project", Project.builder().id("1").priority(1).build());

        List<LeavePolicy> leavePolicies = LeavePolicyCache.getPolicies();

        Map<Boolean, List<LeavePolicy>> analysed = leavePolicies.parallelStream()
                .collect(Collectors.partitioningBy(leavePolicy -> MVEL.executeExpression(leavePolicy.getCompileException(), context, Boolean.class)));

        List<LeavePolicy> leavePoliciesOkay = Optional.ofNullable(analysed.get(Boolean.TRUE)).orElse(new ArrayList<>());
        List<LeavePolicy> leavePoliciesNotOkay = Optional.ofNullable(analysed.get(Boolean.FALSE)).orElse(new ArrayList<>());

        log.info("Leave Policies Okay {}", leavePoliciesOkay);
        log.info("Leave Policies Not Okay {}", leavePoliciesNotOkay);

        if (leavePoliciesOkay.isEmpty())
            return false;
        return true;


    }

    @Override
    public ResponseEntity<ResponseValueObject> recommend(String employeeId) {
        boolean fullWorkLoad = analyze(employeeId);
        LeaveRecommendation leaveRecommendation = LeaveRecommendation.builder().id("1").fullWorkLoad(fullWorkLoad).build();

        try {
            String payload = objectMapper.writeValueAsString(leaveRecommendation);

            return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, payload);
        } catch (Exception ex) {
            log.error("Exception in recommending leave {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, employeeId);
        }


    }
}
