package com.smartgrocery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
