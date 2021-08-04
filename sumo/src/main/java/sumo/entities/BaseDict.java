package sumo.entities;

import java.io.BufferedReader;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

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
    
    BufferedReader b;
    
    
}
