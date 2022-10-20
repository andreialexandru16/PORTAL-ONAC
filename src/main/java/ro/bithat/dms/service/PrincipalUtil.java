package ro.bithat.dms.service;

import ro.bithat.dms.security.SecurityUtils;

public final class PrincipalUtil {

    public static String getThreadLocalPrincipalUsernameLabel() {
        return getPrincipalUsernameLabel(getThreadLocalPrincipalUsername());
    }

    public static String getPrincipalUsernameLabel(String username) {
    	if (username.indexOf("@") <= 0) {
    		return username.substring(0, 1);
    	}
        username = username.split("@")[0];
        StringBuilder usernameSecondInitials = new StringBuilder(username.substring(0,1).toUpperCase());
        String[] usernameParts = username.split(".");
        if(usernameParts.length == 1) {
            usernameParts = username.split(" ");
        }
        if(usernameParts.length == 0) {
            usernameSecondInitials.append(username.substring(username.length() - 1, username.length()).toUpperCase());
        } else {
            for (int i = 1; i < usernameParts.length; ++i) {
                usernameSecondInitials.append(usernameParts[i].substring(0, 1).toUpperCase());
            }
        }
        return  usernameSecondInitials.toString();
    }

    public static String getThreadLocalPrincipalUsername() {
        return SecurityUtils.getUsername();
    }
}
