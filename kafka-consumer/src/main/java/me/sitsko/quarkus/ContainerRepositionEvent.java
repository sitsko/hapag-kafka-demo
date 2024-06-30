package me.sitsko.quarkus;

import lombok.Builder;

/**
 * @author Mikalai Sitsko , 29.06.24
 */
@Builder
public record ContainerRepositionEvent(int eventId) {
}
