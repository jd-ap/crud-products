package io.tech.proof;

public record ErrorDto(String code, String message, String[] details) {
}
