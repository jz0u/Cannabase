package com.cannabase.services;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private final Logger auditLogger;

    public AuditService(Logger auditLogger) {
        this.auditLogger = auditLogger;
    }

    /**
     * Logs an audit event
     * @param action The action being performed
     * @param entityType The type of entity being acted upon
     * @param entityId The ID of the entity
     * @param details Additional details about the action
     */
    public void logEvent(String action, String entityType, Long entityId, String details) {
        String logMessage = String.format(
            "ACTION: %s | ENTITY: %s | ID: %d | DETAILS: %s | TIMESTAMP: %s",
            action,
            entityType,
            entityId,
            details,
            LocalDateTime.now()
        );
        auditLogger.info(logMessage);
    }
}
