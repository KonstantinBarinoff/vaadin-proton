package proton.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseDict extends BaseEntity {

    private String name;

    private String description;

    @Override
    public String toString() {
	return String.format("%s [id=%d, name=%s]", this.getClass().getSimpleName(), this.getId(), this.getName());
    }
}
