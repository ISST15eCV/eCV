package ecv.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Language {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String listeningLevel;
	private String readingLevel;
	private String speakingLevel;
	@ManyToOne
	private Studies studies;
	
	
	public Language() {
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getListeningLevel() {
		return listeningLevel;
	}
	public void setListeningLevel(String listeningLevel) {
		this.listeningLevel = listeningLevel;
	}
	public String getReadingLevel() {
		return readingLevel;
	}
	public void setReadingLevel(String readingLevel) {
		this.readingLevel = readingLevel;
	}
	public String getSpeakingLevel() {
		return speakingLevel;
	}
	public void setSpeakingLevel(String speakingLevel) {
		this.speakingLevel = speakingLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Studies getStudies() {
		return studies;
	}

	public void setStudies(Studies studies) {
		this.studies = studies;
	}
	
	
}
