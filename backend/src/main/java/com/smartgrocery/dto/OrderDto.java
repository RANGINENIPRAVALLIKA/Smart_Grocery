package com.smartgrocery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String deliveryAddress;
    private String paymentMethod;
    private String status;
    private Double totalAmount;
    private LocalDateTime createdAt;
    @Builder.Default
    private List<OrderItemDto> items = new ArrayList<>();
}
