package me.sitsko.quarkus;

import lombok.Builder;

/**
 * @author Mikalai Sitsko , 09.06.24
 */
@Builder
public record Container(String type, String id, String info) {
}
