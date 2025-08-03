package br.com.inmetrics.iuriraredu.models;

/**
 * Represents a bearer token with associated user ID, token string, and session ID.
 */
public record BearerToken(String userId, String token, String sessionId) {
}
