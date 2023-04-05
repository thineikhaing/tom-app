package com.nus.tom.util;

import com.nus.tom.model.Leave;
import com.nus.tom.model.LeavePolicy;
import com.nus.tom.model.LeaveRecommendation;
import com.nus.tom.model.Project;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Mya Pwint
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LeavePolicyCache {
    private static final Map<String, LeavePolicy> leavePolicyMap = new ConcurrentHashMap<>();

    @Value("${recommend.policy}")
    private String mvelString;

    private static Consumer<LeavePolicy> addConsumer = leavePolicy -> {
        leavePolicyMap.put("Policy1", leavePolicy);
    };


    public static List<LeavePolicy> getPolicies() {
        return LeavePolicyCache.leavePolicyMap.values().stream().collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        leavePolicyMap.clear();
        LeavePolicy leavePolicy = LeavePolicy.builder().id("1").name("Policy 1").mvel(mvelString).compileException(new ExpressionCompiler(mvelString).compile()).build();
        addConsumer.accept(leavePolicy);
    }


}
