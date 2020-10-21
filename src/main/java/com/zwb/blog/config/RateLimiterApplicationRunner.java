package com.zwb.blog.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RateLimiterApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadRules();
        System.out.println("load rules");
    }

    private void loadRules() {
        List<FlowRule> rules = new ArrayList<>();
        rules.add(loadRule("getArticle",10));
        FlowRuleManager.loadRules(rules);
    }

    private FlowRule loadRule(String source,int count){
        FlowRule rule = new FlowRule();
        rule.setCount(count);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setResource(source);
        return rule;
    }
}
