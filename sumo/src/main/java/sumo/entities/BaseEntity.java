package sumo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @LastModifiedDate
    @Version
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "MDATE")
    private Date modifiedDate;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "CDATE")
    private Date createdDate;

    @Column(name = "CUSER")  
    private String createdUser;
    
    @Column(name = "MUSER")
    private String modifiedUser;
    
    public boolean isPersisted() {
        return id != null;
    }
    
    @PrePersist
    protected void onCreate() {
	createdUser = SecurityContextHolder.getContext().getAuthentication().getName();
    }    

    @PreUpdate
    protected void onUpdate() {
	modifiedUser = SecurityContextHolder.getContext().getAuthentication().getName();
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
