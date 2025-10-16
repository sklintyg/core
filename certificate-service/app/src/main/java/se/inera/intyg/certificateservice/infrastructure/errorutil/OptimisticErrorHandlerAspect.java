package se.inera.intyg.certificateservice.infrastructure.errorutil;

import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OptimisticErrorHandlerAspect {

  @Around("@annotation(optimisticErrorHandler)")
  public Object retry(ProceedingJoinPoint joinPoint, OptimisticErrorHandler optimisticErrorHandler)
      throws Throwable {
    int maxRetries = optimisticErrorHandler.maxRetries();

    int attempt = 0;

    while (true) {
      try {
        return joinPoint.proceed();
      } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
        attempt++;
        if (attempt >= maxRetries) {
          log.error("OptimisticLockException after {} retries for method: {}",
              maxRetries, joinPoint.getSignature().getName());
          throw new IllegalStateException("Max retries exceeded", e);
        }
        log.warn("OptimisticLockException on attempt {} for method: {}. Retrying...",
            attempt, joinPoint.getSignature().getName());

      }
    }
  }
}
