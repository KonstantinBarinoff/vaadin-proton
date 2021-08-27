package sumo.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Getter  	// View Entity, only getters  
@Table(name = "VIEW_WORKFOLDERSCATALOG")   
public class WorkFolderEntity {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PURPOSE")
	private String purpose;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "OWNERNAME")
	private String ownerName;

	@Override
	public String toString() {
		return String.format("WorkFolder [id=%d, Name='%s', Purpose='%s']", id, name, purpose);
	}

}
