package sumo.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEquip {

    private Integer registrationNB;
    private String serialNB;
    private String groupName;
    private String modelName;
    private String locationName;
    private String userName;
    private String login;
    private String phone;
    private String title;

    public Integer getId() {
	return registrationNB;
    }
    
    @Override
    public int hashCode() {
	if (getId() != null) {
	    return getId().hashCode();
	}
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	UserEquip other = (UserEquip) obj;
	if (getId() == null || other.getId() == null) {
	    return false;
	}
	return getId().equals(other.getId());
    }

    @Override
    public String toString() {
	return String.format("%s [id=%d]", this.getClass().getSimpleName(), this.getId());
    }

}
