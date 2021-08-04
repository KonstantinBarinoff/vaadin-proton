//package proton.entities;
//
//public class UserEquip {
//
//    private Integer registrationNB;
//    private String serialNB;
//    private String groupName;
//    private String modelName;
//    private String locationName;
//    private String userName;
//    private String login;
//
//    public String getLogin() {
//        return login;
//    }
//
//    @Override
//    public int hashCode() {
//	final int prime = 31;
//	int result = 1;
//	result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
//	result = prime * result + ((locationName == null) ? 0 : locationName.hashCode());
//	result = prime * result + ((login == null) ? 0 : login.hashCode());
//	result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
//	result = prime * result + ((registrationNB == null) ? 0 : registrationNB.hashCode());
//	result = prime * result + ((serialNB == null) ? 0 : serialNB.hashCode());
//	result = prime * result + ((userName == null) ? 0 : userName.hashCode());
//	return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//	if (this == obj)
//	    return true;
//	if (obj == null)
//	    return false;
//	if (getClass() != obj.getClass())
//	    return false;
//	UserEquip other = (UserEquip) obj;
//	if (groupName == null) {
//	    if (other.groupName != null)
//		return false;
//	} else if (!groupName.equals(other.groupName))
//	    return false;
//	if (locationName == null) {
//	    if (other.locationName != null)
//		return false;
//	} else if (!locationName.equals(other.locationName))
//	    return false;
//	if (login == null) {
//	    if (other.login != null)
//		return false;
//	} else if (!login.equals(other.login))
//	    return false;
//	if (modelName == null) {
//	    if (other.modelName != null)
//		return false;
//	} else if (!modelName.equals(other.modelName))
//	    return false;
//	if (registrationNB == null) {
//	    if (other.registrationNB != null)
//		return false;
//	} else if (!registrationNB.equals(other.registrationNB))
//	    return false;
//	if (serialNB == null) {
//	    if (other.serialNB != null)
//		return false;
//	} else if (!serialNB.equals(other.serialNB))
//	    return false;
//	if (userName == null) {
//	    if (other.userName != null)
//		return false;
//	} else if (!userName.equals(other.userName))
//	    return false;
//	return true;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public UserEquip() {};	
//    
//    public UserEquip(Integer registrationNB, String serialNB, String groupName, String modelName,
//	    String locationName, String userName) {
//	this.registrationNB = registrationNB;
//	this.serialNB = serialNB;
//	this.groupName = groupName;
//	this.modelName = modelName;
//	this.locationName = locationName;
//	this.userName = userName;
//    }
//
//    public String getUserName() {
//	return userName;
//    }
//    
//    public void setUserName(String userName) {
//	this.userName = userName;
//    }
//    public void setRegistrationNB(Integer registrationNB) {
//        this.registrationNB = registrationNB;
//    }
//
//    public void setSerialNB(String serialNB) {
//        this.serialNB = serialNB;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public void setModelName(String modelName) {
//        this.modelName = modelName;
//    }
//
//    public void setLocationName(String locationName) {
//        this.locationName = locationName;
//    }
//
//    public Integer getRegistrationNB() {
//	return registrationNB;
//    }
//
//    public String getSerialNB() {
//	return serialNB;
//    }
//
//    public String getGroupName() {
//	return groupName;
//    }
//
//    public String getModelName() {
//	return modelName;
//    }
//
//    public String getLocationName() {
//	return locationName;
//    }
//
//}
