package ecv.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Career implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(mappedBy="career", fetch=FetchType.EAGER)
	private CurrentJob currentJob;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="career")
	private Collection<CareerItem> previousJobs;
	@OneToOne
	private Usuario usuario;

	public Career() {
	}
	
	public CurrentJob getCurrentJob() {
		return currentJob;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setCurrentJob(CurrentJob currentJob) {
		this.currentJob = currentJob;
	}
	public Collection<CareerItem> getPreviousJobs() {
		return previousJobs;
	}
	public void setPreviousJobs(Collection<CareerItem> previousJobs) {
		this.previousJobs = previousJobs;
	}
}
