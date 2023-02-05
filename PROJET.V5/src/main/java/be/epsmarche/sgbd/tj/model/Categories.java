package be.epsmarche.sgbd.tj.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Hannart Thierry-Julien
 */
@Entity
@Table(name = "categories")
public class Categories implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categorie")
	int id;
	
	@Column(name = "nom_categorie", nullable = false, length = 50)
	String nom;
	
	@Column(name = "actif_categorie", nullable = false)
	boolean actif;

	@OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
	private List<Articles> artfk1;

	
	public Categories() {
		super();
	}

	
	public Categories(int id, String nom, boolean actif) {
		super();
		this.id = id;
		this.nom = nom;
		this.actif = actif;
	}

	
	public Categories(String nom, boolean actif) {
		super();
		this.nom = nom;
		this.actif = actif;
	}

	
	public int getId() {
		return id;
	}

	
	public String getNom() {
		return nom;
	}

	
	public void setNom(String nom) {
		this.nom = nom;
	}

	
	public boolean isActif() {
		return actif;
	}

	
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	
	public List<Articles> getArtfk1() {
		return artfk1;
	}

	
	public void setArtfk1(List<Articles> artfk1) {
		this.artfk1 = artfk1;
	}
}
