package pinstack_api.scheduled;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pinstack_api.repositories.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCleanupScheduler {
    private final UserRepository repository;

    @Scheduled(fixedRate = 900000) 
    public void cleanUpExpiredUnverifiedUsers() {
        log.info("Starting cleanup of unverified users with expired code...");
        try {
            LocalDateTime now = LocalDateTime.now();
            repository.deleteByVerifiedFalseAndCodeExpiresAtBefore(now);
            
            log.info("Cleanup of inactive users completed successfully.");
        } catch (Exception e) {
            log.error("Error during cleanup of expired users: {}", e.getMessage());
        }
    }
}
