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
public class OptimisticLockErrorHandlerAspect {

  @Around("@annotation(optimisticLockErrorHandler)")
  public Object retry(ProceedingJoinPoint joinPoint,
      OptimisticLockErrorHandler optimisticLockErrorHandler)
      throws Throwable {
    final var maxRetries = optimisticLockErrorHandler.maxRetries();

    var attempt = 0;

    while (true) {
      try {
        return joinPoint.proceed();
      } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
        attempt++;
        if (attempt >= maxRetries) {
          throw new IllegalStateException(String.format(
              "OptimisticLockException after %s retries for method: %s",
              maxRetries, joinPoint.getSignature().getName()));
        }
        log.warn("OptimisticLockException on attempt {} for method: {}. Retrying...",
            attempt, joinPoint.getSignature().getName());

      }
    }
  }
}
