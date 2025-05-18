package com.sismics.docs.core.dao.dto;

import com.google.common.base.MoreObjects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

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

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("username")
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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("status")
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

    public UserRequestDto(String id, String username, String email, String status, Date createDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
        this.createTimestamp = createDate != null ? createDate.getTime() : null;
    }

    public UserRequestDto() {}
}