package com.nus.tom.model.enums;

import com.nus.tom.service.EmailBuilder;
import com.nus.tom.service.impl.LeaveSubmisionEmailBuilder;
import com.nus.tom.service.impl.LeaveWorkflowEmailBuilder;
import com.nus.tom.service.impl.RegistrationEmailBuilder;

/**
 * @author Mya Pwint
 */
public enum EmailStrategy {
    REGISTRATION("REGISTRATION") {
        @Override
        public EmailBuilder getEmailBuilder() {
            return new RegistrationEmailBuilder();
        }
    },
    LEAVE_WORKFLOW("LEAVE_WORKFLOW") {
        @Override
        public EmailBuilder getEmailBuilder() {
            return new LeaveWorkflowEmailBuilder();
        }
    },

    LEAVE_SUBMIT("LEAVE_SUBMIT") {
        @Override
        public EmailBuilder getEmailBuilder() {
            return new LeaveSubmisionEmailBuilder();
        }
    };

    public String type;

    EmailStrategy(String type) {
        this.type = type;
    }

    public abstract EmailBuilder getEmailBuilder();

    @Override
    public String toString() {
        return type;
    }


}
