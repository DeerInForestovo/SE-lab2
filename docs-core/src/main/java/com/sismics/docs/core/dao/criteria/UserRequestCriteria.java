package com.sismics.docs.core.dao.criteria;

/**
 * UserRequest criteria.
 * Used to filter user request entities when querying.
 */
public class UserRequestCriteria {
    /**
     * Search query (applied to username or email).
     */
    private String search;

    /**
     * User request ID.
     */
    private String requestId;

    /**
     * Username.
     */
    private String username;

    /**
     * Email address.
     */
    private String email;

    /**
     * Status of the request.
     */
    private String status;

    public String getSearch() {
        return search;
    }

    public UserRequestCriteria setSearch(String search) {
        this.search = search;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public UserRequestCriteria setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRequestCriteria setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRequestCriteria setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserRequestCriteria setStatus(String status) {
        this.status = status;
        return this;
    }
}
