package com.sismics.docs.core.dao;

import com.sismics.docs.core.constant.AuditLogType;
import com.sismics.docs.core.dao.criteria.UserRequestCriteria;
import com.sismics.docs.core.dao.dto.UserRequestDto;
import com.sismics.docs.core.model.jpa.UserRequest;
import com.sismics.docs.core.util.AuditLogUtil;
import com.sismics.docs.core.util.jpa.QueryParam;
import com.sismics.docs.core.util.jpa.QueryUtil;
import com.sismics.docs.core.util.jpa.SortCriteria;
import com.sismics.util.context.ThreadLocalContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

/**
 * DAO for user registration requests.
 */
public class UserRequestDao {

    /**
     * Creates a new user request.
     *
     * @param userRequest UserRequest to create
     * @return The generated ID
     * @throws Exception if a request with the same username or email already exists
     */
    public String create(UserRequest userRequest) throws Exception {
        EntityManager em = ThreadLocalContext.get().getEntityManager();

        // Check for existing username or email (not deleted)
        Query q = em.createQuery(
                "select r from UserRequest r where (r.username = :username or r.email = :email) and r.deleteDate is null");
        q.setParameter("username", userRequest.getUsername());
        q.setParameter("email", userRequest.getEmail());
        List<?> l = q.getResultList();
        if (!l.isEmpty()) {
            throw new Exception("DuplicateUsernameOrEmail");
        }

        userRequest.setId(UUID.randomUUID().toString());
        userRequest.setCreateDate(new Date());
        userRequest.setStatus("pending");
        em.persist(userRequest);

        AuditLogUtil.create(userRequest, AuditLogType.CREATE, null);

        return userRequest.getId();
    }

    /**
     * Gets a user request by ID.
     *
     * @param id The request ID
     * @return UserRequest or null
     */
    public UserRequest getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(UserRequest.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Deletes a user request (soft delete).
     *
     * @param id Request ID
     * @param userId User performing the deletion
     */
    public void delete(String id, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        UserRequest userRequest = em.find(UserRequest.class, id);
        if (userRequest != null && userRequest.getDeleteDate() == null) {
            userRequest.setDeleteDate(new Date());
            AuditLogUtil.create(userRequest, AuditLogType.DELETE, userId);
        }
    }

    /**
     * Updates the status of a user request.
     *
     * @param id Request ID
     * @param newStatus New status (accepted/rejected)
     * @param userId User performing the update
     */
    public void updateStatus(String id, String newStatus, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        UserRequest userRequest = em.find(UserRequest.class, id);
        if (userRequest != null && userRequest.getDeleteDate() == null) {
            userRequest.setStatus(newStatus);
            AuditLogUtil.create(userRequest, AuditLogType.UPDATE, userId);
        }
    }

    /**
     * Finds all pending user requests.
     *
     * @return List of pending requests
     */
    @SuppressWarnings("unchecked")
    public List<UserRequest> getPendingRequests() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select r from UserRequest r where r.status = :status and r.deleteDate is null");
        q.setParameter("status", "pending");
        return q.getResultList();
    }

    /**
     * Returns the list of user requests based on criteria.
     * 
     * @param criteria Search criteria for user requests
     * @param sortCriteria Sorting criteria
     * @return List of user requests
     */
    public List<UserRequestDto> findByCriteria(UserRequestCriteria criteria, SortCriteria sortCriteria) {
        HashMap<String, Object> parameterMap = new HashMap<>();
        List<String> criteriaList = new ArrayList<>();
        
        StringBuilder sb = new StringBuilder("select ur.UR_ID_C as c0, ur.UR_USERNAME_C as c1, ur.UR_EMAIL_C as c2, ur.UR_STATUS_C as c3, ur.UR_CREATEDATE_D as c4, ur.UR_REQUESTID_C as c5");
        sb.append(" from T_USER_REQUEST ur ");
        
        // Add search criteria
        if (criteria.getSearch() != null) {
            criteriaList.add("lower(ur.UR_USERNAME_C) like lower(:search) or lower(ur.UR_EMAIL_C) like lower(:search)");
            parameterMap.put("search", "%" + criteria.getSearch() + "%");
        }
        if (criteria.getRequestId() != null) {
            criteriaList.add("ur.UR_ID_C = :requestId");
            parameterMap.put("requestId", criteria.getRequestId());
        }
        if (criteria.getUsername() != null) {
            criteriaList.add("ur.UR_USERNAME_C = :username");
            parameterMap.put("username", criteria.getUsername());
        }
        if (criteria.getEmail() != null) {
            criteriaList.add("ur.UR_EMAIL_C = :email");
            parameterMap.put("email", criteria.getEmail());
        }
        if (criteria.getStatus() != null) {
            criteriaList.add("ur.UR_STATUS_C = :status");
            parameterMap.put("status", criteria.getStatus());
        }
        
        // Assuming there's no "deleted" column in this case, but if there were, add similar condition as in user.
        
        // If criteria are provided, append them to the query
        if (!criteriaList.isEmpty()) {
            sb.append(" where ");
            sb.append(String.join(" and ", criteriaList));
        }
        
        // Perform the search with sorting
        QueryParam queryParam = QueryUtil.getSortedQueryParam(new QueryParam(sb.toString(), parameterMap), sortCriteria);
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = QueryUtil.getNativeQuery(queryParam).getResultList();
        
        // Assemble results into a list of UserRequestDto
        List<UserRequestDto> userRequestDtoList = new ArrayList<>();
        for (Object[] o : resultList) {
            int i = 0;
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setId((String) o[i++]);
            userRequestDto.setUsername((String) o[i++]);
            userRequestDto.setEmail((String) o[i++]);
            userRequestDto.setStatus((String) o[i++]);
            userRequestDto.setCreateTimestamp(((Timestamp) o[i++]).getTime());
            userRequestDto.setId((String) o[i++]);
            userRequestDtoList.add(userRequestDto);
        }
        return userRequestDtoList;
    }
    
    public List<UserRequestDto> findAll() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        TypedQuery<UserRequestDto> query = em.createQuery(
            "select new com.sismics.docs.core.dao.dto.UserRequestDto(r.id, r.username, r.email, r.status, r.createDate) " +
            "from UserRequest r where r.deleteDate is null order by r.createDate desc", UserRequestDto.class);
        return query.getResultList();
    }
    
}
