package com.edusol.retailbanking.application.security;

import com.edusol.retailbanking.application.SpringAppicationContext;

public class SecurityConstant {
    public static  final long EXPITATION_TIME=864000000; //10 DAYS
    public static  final String TOKEN_PREFIX="Bearer";
    public static  final String HEADER_STRING="Authorization";
    public static  final String SIGN_UP_URL="/users/createUser";
    //public static  final String TOKEN_SECRET="jf9i4jgu83nfl0";

    public static String  getTokenSecret()
    {
        AppProperties appProperties=(AppProperties) SpringAppicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
