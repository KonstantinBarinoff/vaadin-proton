package sumo.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @LastModifiedDate
    @Version
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "MODIFIEDAT")
    private Date modifiedAt;

    @Column(name = "MODIFIEDBY")
    private String modifiedBy;
    
    public boolean isPersisted() {
        return id != null;
    }
    
    @PrePersist
    protected void onCreate() {
	createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }    

    @PreUpdate
    protected void onUpdate() {
	modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
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
	BaseEntity other = (BaseEntity) obj;
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
