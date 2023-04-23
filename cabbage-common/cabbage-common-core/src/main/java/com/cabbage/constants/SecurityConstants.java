package com.cabbage.constants;

public interface SecurityConstants {

    String[] DEFAULT_IGNORE_URLS = {"/actuator/**", "/error", "/v3/api-docs"};

    String USER_INFO="user_info";

    String REDIRECT_URL="redirect_url";

    String AUTHORIZATION_CODE_MODE_URL="/login/confirm_authorization";


}
