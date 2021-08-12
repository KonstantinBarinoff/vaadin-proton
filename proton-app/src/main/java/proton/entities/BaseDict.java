package proton.entities;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class BaseDict extends BaseEntity {

    @NotNull
    private String name = "";

    private String description;

    @Override
    public String toString() {
        return String.format("%s [id=%d, name=%s]", this.getClass().getSimpleName(), this.getId(), this.getName());
    }


    public @NotNull String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
