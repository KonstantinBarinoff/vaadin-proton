package proton.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Общий предок всех сущностей.
 * Все таблицы БД должны включать эти поля!
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    /**
     * Первичный ключ, автогенерируемый на стороне MSSQL
     * MSSQL: ID bigint IDENTITY(1,1) NOT NULL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Для поддержки Оптимистических блокировок Hibernate увеличивает поле version
     * при каждом изменении записи. <p>
     * Значение NULL недопустимо - ломается логика JPA (Insert вместо Update) <p>
     * MSSQL: Version int NOT NULL CONSTRAINT DF_version DEFAULT 0
     */
    @Version
    private int version;

    /**
     * Дата/время создания записи
     * MSSQL: Created_At datetime NULL
     * */
    // TODO: Попробовать совместную генерацию с DEFAULT значениями на стороне БД
    @CreatedDate
    private LocalDateTime createdAt;

    /** Дата/время изменения записи*/
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /** Учетная запись пользователя, создавшего запись */
    private String createdBy;

    /** Учетная запись пользователя последнего изменения записи */
    private String modifiedBy;

    // TODO: Протестировать
    public boolean isPersisted() {
        return id != null;
    }

    /**
     * Сохранение login пользователя создавшего запись
     * Обходим в Тестах, т.к. при выполнения Теста SecurityContext не создается
     */
    @PrePersist
    // TODO: Private?
    protected void onCreate() {
        if (isJUnitTest()) {
            return;
        }
        createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Сохранение login пользователя изменившего запись
     * Обходим в Тестах, т.к. при выполнения Теста SecurityContext не создается
     */
    @PreUpdate
    // TODO: Private?
    protected void onUpdate() {
        if (isJUnitTest()) {
            return;
        }
        modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (obj instanceof BaseEntity && obj.getClass().equals(getClass())) {
            return this.id.equals(((BaseEntity) obj).id);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d]", this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Проверка на выполнение внутри теста
     */
    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
