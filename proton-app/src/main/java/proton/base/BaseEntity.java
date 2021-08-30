package proton.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // значение NULL недопустимо - ломается логика JPA (Insert вместо Update)
    // Version int NOT NULL CONSTRAINT DF_version DEFAULT 0
    @Version
    private int version;

    // MSSQL: DATETIME
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private String createdBy;

    private String modifiedBy;

    public boolean isPersisted() {
        return id != null;
    }

    @PrePersist
    protected void onCreate() {
        /** Внутри Теста SecurityContext не создается */
        if (isJUnitTest()) {
            return;
        }
        createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PreUpdate
    protected void onUpdate() {
        /** Внутри Теста SecurityContext не создается */
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
     * Проверка на выполнения внутри теста
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
