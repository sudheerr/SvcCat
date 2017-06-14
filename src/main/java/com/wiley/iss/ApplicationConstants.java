package com.wiley.iss;

import java.util.HashMap;

public interface ApplicationConstants {

    String USER_MAIL="user_mail";
    String USER_DTO="USER_DTO";
    //String WEB_PROPS = "WEB_PROPS";
    //String GLOBAL_CONFIG_VO="GLOBAL_CONFIG_VO";
    //String USER_DN="user_dn";

   HashMap<String,Integer> status = new HashMap<String, Integer>(){{
       put("ACTIVE",1);
       put("INACTIVE",2);
       put("INPROGRESS",3);
       put("TBD",4);
   }};

}
