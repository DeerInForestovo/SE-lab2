package com.sismics.docs.core.model.jpa;

import com.google.common.base.MoreObjects;
import jakarta.persistence.*;

import java.util.Date;

/**
 * User registration request entity.
 */
@Entity
@Table(name = "T_USER_REQUEST")
public class UserRequest implements Loggable {

    @Id
    @Column(name = "USR_ID_C", length = 36)
    private String id;

    @Column(name = "USR_USERNAME_C", nullable = false, length = 50)
    private String username;

    @Column(name = "USR_EMAIL_C", nullable = false, length = 100)
    private String email;

    @Column(name = "USR_PASSWORD_C", nullable = false, length = 60)
    private String password;

    @Column(name = "USR_STATUS_C", nullable = false, length = 20)
    private String status;

    @Column(name = "USR_CREATEDATE_D", nullable = false)
    private Date createDate;

    @Column(name = "USR_REVIEWDATE_D")
    private Date reviewDate;

    @Column(name = "USR_REVIEW_COMMENT_C", length = 500)
    private String reviewComment;
    
    @Column(name = "USR_DELETEDATE_D")
    private Date deleteDate;
    
    @Override
    public Date getDeleteDate() {
        return deleteDate;
    }
    
    public UserRequest setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public String getId() {
        return id;
    }

    public UserRequest setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public UserRequest setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public UserRequest setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public UserRequest setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
        return this;
    }

    @Override
    public String toMessage() {
        return username;
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
