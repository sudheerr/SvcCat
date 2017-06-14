package com.wiley.iss.user;

public class UserDTO {

    private String uid;
    //private String jwsObjectID;
    private String userName;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    //	public String getJwsObjectID() {
//		return jwsObjectID;
//	}
//	public void setJwsObjectID(String jwsObjectID) {
//		this.jwsObjectID = jwsObjectID;
//	}
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDTO [uid=" + uid
                + ", userName=" + userName + ", email=" + email + "]";
    }

}
