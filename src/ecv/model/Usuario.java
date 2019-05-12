package ecv.model;

import java.io.*;
import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Usuario implements Serializable{

	@Id private String email;
	private String password;
	@OneToOne(mappedBy="usuario", fetch=FetchType.EAGER)
	private PersonalData pd;
	@OneToOne(mappedBy="usuario", fetch=FetchType.EAGER)
	private Career career;
	@OneToOne(mappedBy="usuario", fetch=FetchType.EAGER)
	private Studies studies;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="usuario")
	private Collection<CV> cvs;
	
	private String orcidId;
	private String accessToken;
	
	@Lob
	private byte[] photo;
	
	
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] foto) {
		this.photo = foto;
	}

	public String getOrcidId() {
		return orcidId;
	}

	public void setOrcidId(String orcidId) {
		this.orcidId = orcidId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Collection<CV> getCvs() {
		return cvs;
	}

	public void setCvs(Collection<CV> cvs) {
		this.cvs = cvs;
	}

	public Usuario() {}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PersonalData getPd() {
		return pd;
	}
	public void setPd(PersonalData pd) {
		this.pd = pd;
	}
	public Career getCareer() {
		return career;
	}
	public void setCareer(Career career) {
		this.career = career;
	}
	public Studies getStudies() {
		return studies;
	}
	public void setStudies(Studies studies) {
		this.studies = studies;
	}
}
