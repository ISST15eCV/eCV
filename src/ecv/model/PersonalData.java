package ecv.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class PersonalData implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private Gender gender;
	private String nif;
	private java.sql.Date bday;
	private Integer tlf;
	private Integer mobile;
	private Integer fax;
	private String web;
	private String textFree;
	@OneToOne(mappedBy="pd", fetch=FetchType.EAGER)
	private Address address;
	@OneToOne(mappedBy="pd", fetch=FetchType.EAGER)
	private Nationality nationality;
	@OneToOne
	private Usuario usuario;
	
	public PersonalData() {
		
	}
	
	

	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public java.sql.Date getBday() {
		return bday;
	}
	public void setBday(java.sql.Date bday) {
		this.bday = bday;
	}
	public Integer getTlf() {
		return tlf;
	}
	public void setTlf(Integer tlf) {
		this.tlf = tlf;
	}
	public Integer getMobile() {
		return mobile;
	}
	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}
	public Integer getFax() {
		return fax;
	}
	public void setFax(Integer fax) {
		this.fax = fax;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Nationality getNationality() {
		return nationality;
	}
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTextFree() {
		return textFree;
	}

	public void setTextFree(String textFree) {
		this.textFree = textFree;
	}
	
	
	
}

