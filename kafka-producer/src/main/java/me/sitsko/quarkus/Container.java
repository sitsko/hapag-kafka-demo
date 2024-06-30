package me.sitsko.quarkus;

import lombok.Builder;

/**
 * Value object, shipment container
 *
 * @author Mikalai Sitsko , 09.06.24
 */
@Builder
public record Container(String type, int id, String info) {
}
