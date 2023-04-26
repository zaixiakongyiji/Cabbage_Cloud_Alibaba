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

    String PROJECT_OAUTH_ACCESS = "token::access_token";

    String PROJECT_OAUTH_TOKEN = "cabbage_oauth:token:";

    /**
     * 菜单信息缓存
     */
    String MENU_DETAILS = "menu_details";

    /**
     * 用户信息缓存
     */
    String USER_DETAILS = "user_details";

    /**
     * 字典信息缓存
     */
    String DICT_DETAILS = "dict_details";

    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "client:details";

    /**
     * 参数缓存
     */
    String PARAMS_DETAILS = "params_details";


    long CODE_TIME = 60;

}
