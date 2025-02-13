package net.mahtabalam.dto;

import java.math.BigDecimal;

public record TransferRequest(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount) {
}