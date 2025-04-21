package com.sismics.docs.rest.resource;

import com.google.common.collect.Lists;
import com.sismics.docs.rest.constant.BaseFunction;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.security.IPrincipal;
import com.sismics.security.UserPrincipal;
import com.sismics.util.filter.SecurityFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Base class of REST resources.
 * 
 * @author jtremeaux
 */
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BaseResource {
    /**
     * @apiDefine admin Admin
     * Only the admin user can access this resource
     */

    /**
     * @apiDefine user Authenticated user
     * All authenticated users can access this resource
     */

    /**
     * @apiDefine none Anonymous user
     * This resource can be accessed anonymously
     */

    /**
     * @apiDefine server Server error
     */

    /**
     * @apiDefine client Client error
     */

    /**
     * Injects the HTTP request.
     */
    @Context
    protected HttpServletRequest request;
    
    /**
     * Application key.
     */
    @QueryParam("app_key")
    protected String appKey;
    
    /**
     * Principal of the authenticated user.
     */
    protected IPrincipal principal;

    /**
     * This method is used to check if the user is authenticated.
     * 
     * @apiSuccess True if the user is authenticated and not anonymous
     */
    protected boolean authenticate() {
        Principal principal = (Principal) request.getAttribute(SecurityFilter.PRINCIPAL_ATTRIBUTE);
        if (principal instanceof IPrincipal) {
            this.principal = (IPrincipal) principal;
            return !this.principal.isAnonymous();
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the user has a base function. Throw an exception if the check fails.
     * 
     * @apiParam baseFunction Base function to check
     */
    void checkBaseFunction(BaseFunction baseFunction) {
        if (!hasBaseFunction(baseFunction)) {
            throw new ForbiddenClientException();
        }
    }
    
    /**
     * Checks if the user has a base function.
     * 
     * @apiParam baseFunction Base function to check
     * @apiSuccess True if the user has the base function
     */
    boolean hasBaseFunction(BaseFunction baseFunction) {
        if (!(principal instanceof UserPrincipal)) {
            return false;
        }
        Set<String> baseFunctionSet = ((UserPrincipal) principal).getBaseFunctionSet();
        return baseFunctionSet != null && baseFunctionSet.contains(baseFunction.name());
    }
    
    /**
     * Returns a list of ACL target ID.
     * 
     * @apiParam shareId Share ID (optional)
     * @apiSuccess List of ACL target ID
     */
    List<String> getTargetIdList(String shareId) {
        List<String> targetIdList = Lists.newArrayList(principal.getGroupIdSet());
        if (principal.getId() != null) {
            targetIdList.add(principal.getId());
        }
        if (shareId != null) {
            targetIdList.add(shareId);
        }
        return targetIdList;
    }
}
