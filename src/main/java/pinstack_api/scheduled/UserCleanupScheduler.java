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
        log.info("Iniciando limpeza de usuários não verificados com código expirado...");
        
        try {
            LocalDateTime now = LocalDateTime.now();
            repository.deleteByVerifiedFalseAndCodeExpiresAtBefore(now);
            
            log.info("Limpeza de usuários inativos concluída com sucesso.");
        } catch (Exception e) {
            log.error("Erro durante a limpeza de usuários expirados: {}", e.getMessage());
        }
    }
}
