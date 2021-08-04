//package proton.workfolders;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "VIEW_WORKFOLDERSCATALOG")
//public class WorkFolders {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ID")
//	private Long id;
//
//	@Column(name = "NAME")
//	private String name;
//
//	@Column(name = "PURPOSE")
//	private String purpose;
//
//	@Column(name = "DESCRIPTION")
//	private String description;
//
//	@Column(name = "OWNERNAME")
//	private String ownerName;
//
//	public String getDescription() {
//	    return description;
//	}
//
////	public void setDescription(String description) {
////	    this.description = description;
////	}
//
//	public String getOwnerName() {
//	    return ownerName;
//	}
//
////	public void setOwnerName(String ownerName) {
////	    this.ownerName = ownerName;
////	}
//
//
//	protected WorkFolders() {
//	}
//
//	public WorkFolders(String name, String purpose) {
//		this.name = name;
//		this.purpose = purpose;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
////	public void setName(String name) {
////		this.name = name;
////	}
//
//	public String getPurpose() {
//		return purpose;
//	}
//
////	public void setPurpose(String purpose) {
////		this.purpose = purpose;
////	}
//
//	@Override
//	public String toString() {
//		return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, name, purpose);
//	}
//
//}
