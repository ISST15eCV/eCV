package ecv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CurrentJob {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String category;
	private String employer;
	private java.sql.Date start;
	@OneToOne
	private Career career;
	
	public CurrentJob() {
		
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		System.out.println("hoal");
		this.category = category;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public java.sql.Date getStart() {
		return start;
	}
	public void setStart(java.sql.Date start) {
		this.start = start;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
	}
	
	
}