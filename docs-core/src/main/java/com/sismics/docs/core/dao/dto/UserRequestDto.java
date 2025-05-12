package com.sismics.docs.core.dao.dto;

import com.google.common.base.MoreObjects;

/**
 * User request DTO.
 * Used to represent a pending user registration request.
 */
public class UserRequestDto {
    /**
     * Request ID.
     */
    private String id;

    /**
     * Username.
     */
    private String username;

    /**
     * Encrypted password.
     */
    private String password;

    /**
     * Email address.
     */
    private String email;

    /**
     * Request status (e.g. pending, accepted, rejected).
     */
    private String status;

    /**
     * Creation timestamp.
     */
    private Long createTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("email", email)
                .add("status", status)
                .toString();
    }
}
