package io.tech.proof.error;

public record ErrorDto(String code, String message, String[] details) {
}
