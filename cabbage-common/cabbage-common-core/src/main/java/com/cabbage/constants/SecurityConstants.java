package com.cabbage.constants;

public interface SecurityConstants {

    String[] DEFAULT_IGNORE_URLS = {"/actuator/**", "/error", "/v3/api-docs"};

    String USER_INFO="user_info";

    String PASSWORD="password";

    String USERNAME="username";


    String ROLE = "ROLE_";

    String REDIRECT_URL="redirect_url";

    String AUTHORIZATION_CODE_MODE_URL="/login/confirm_authorization";


    String ASE="AES";

    String OAUTH_LOGIN_URL="/oauth2/login";

    String REFRESH_TOKEN = "refresh_token";

    String ACCESS_TOKEN = "access_token";

    String CODE_LENGTH = "6";


    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";
    String SMS_PARAMETER_NAME = "phone";
}
