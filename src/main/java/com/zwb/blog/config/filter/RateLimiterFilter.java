package com.zwb.blog.config.filter;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RateLimiterFilter implements Filter {

    private boolean hasLoad = false;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!hasLoad){
            System.out.println("add flow rule");
            loadRules();
            hasLoad = true;
        }
        filterChain.doFilter(servletRequest,servletResponse);
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
