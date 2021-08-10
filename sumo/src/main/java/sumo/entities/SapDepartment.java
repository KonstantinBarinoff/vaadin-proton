package sumo.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SapDepartment {

    private Integer departmentNumber;
    private Integer departmentParentNumber;
    private String departmentName;
    private SapDepartment parent;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SapDepartment that = (SapDepartment) o;

        if (!departmentNumber.equals(that.departmentNumber)) return false;
        if (departmentParentNumber != null ? !departmentParentNumber.equals(that.departmentParentNumber) : that.departmentParentNumber != null)
            return false;
        return departmentName.equals(that.departmentName);
    }

    @Override
    public String toString() {
        return "BzemDepartment [departmentNumber=" + departmentNumber + ", departmentParentNumber="
                + departmentParentNumber + ", departmentName=" + departmentName + "]";
    }

}
