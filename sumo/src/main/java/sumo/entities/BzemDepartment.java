package sumo.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BzemDepartment {

    private Integer departmentNumber;
    private Integer departmentParentNumber;
    private String departmentName;
    private BzemDepartment parent;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
        result = prime * result + ((departmentNumber == null) ? 0 : departmentNumber.hashCode());
        result = prime * result + ((departmentParentNumber == null) ? 0 : departmentParentNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BzemDepartment other = (BzemDepartment) obj;
        if (departmentName == null) {
            if (other.departmentName != null)
                return false;
        } else if (!departmentName.equals(other.departmentName))
            return false;
        if (departmentNumber == null) {
            if (other.departmentNumber != null)
                return false;
        } else if (!departmentNumber.equals(other.departmentNumber))
            return false;
        if (departmentParentNumber == null) {
            if (other.departmentParentNumber != null)
                return false;
        } else if (!departmentParentNumber.equals(other.departmentParentNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BzemDepartment [departmentNumber=" + departmentNumber + ", departmentParentNumber="
                + departmentParentNumber + ", departmentName=" + departmentName + "]";
    }

}
