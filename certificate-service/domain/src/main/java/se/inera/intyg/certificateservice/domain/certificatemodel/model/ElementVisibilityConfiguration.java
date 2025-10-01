package se.inera.intyg.certificateservice.domain.certificatemodel.model;

/**
 * Interface for configurations that determine visibility of certificate elements.
 *
 * <p>Implementations carry the information necessary to evaluate whether a specific
 * element (or a complement element) should be visible in a certificate. The configuration is
 * attached to an element specification and is used by visibility handlers to decide if the element
 * should be shown to the user.
 *
 * <p>Implementations expose a {@link ElementType} via {@link #type()}
 * so that dispatching code can route to the correct handler for evaluation.
 */
public interface ElementVisibilityConfiguration {

  ElementType type();
}