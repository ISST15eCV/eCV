package ecv.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Studies implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="studies")
	private Collection<Title> degrees;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="studies")
	private Collection<Phd> phds;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="studies")
	private Collection<Language> languages;
	@OneToOne
	private Usuario usuario;
	
	public Studies() {
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
	public Collection<Title> getDegrees() {
		return degrees;
	}
	public void setDegrees(Collection<Title> degrees) {
		this.degrees = degrees;
	}
	public Collection<Phd> getPhds() {
		return phds;
	}
	public void setPhds(Collection<Phd> phds) {
		this.phds = phds;
	}
	public Collection<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(Collection<Language> languages) {
		this.languages = languages;
	}
	
}
