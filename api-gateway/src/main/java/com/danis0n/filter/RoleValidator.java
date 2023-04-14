package com.danis0n.filter;

public interface RoleValidator {

    final static String NONE_ROLE = "NONE";

    boolean validate(String requiredRole, String currentRole);

}
