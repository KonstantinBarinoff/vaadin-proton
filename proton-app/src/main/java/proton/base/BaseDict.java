package proton.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

/**
 * Расширение для сущностей представляющих из себя вид справочников,
 * т.е. имеющих поля Наименование и Примечание (Описание)
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseDict extends BaseEntity {

    /** Наименование */
    protected String name;

    /** Примечание */
    private String description;

    @Override
    public String toString() {
        return String.format("%s [id=%d, name=%s]", this.getClass().getSimpleName(), this.getId(), this.getName());
    }
}
