package be.epsmarche.sgbd.tj.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
@Table(name = "commandes")
public class Commandes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_commande")
	int id;
	
	@Column(name = "nom_commande", nullable = false, length = 50)
	String nom;
	
	@Column(name = "prenom_commande", nullable = false, length = 50)
	String prenom;
	
	@Column(name = "date_commande", nullable = false)
	LocalDateTime date;
	
	@Column(name = "cloture_commande", nullable = false)
	boolean cloture;

	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
	private List<ListesArticle> listefk2;

	
	public Commandes() {
	}

	
	public Commandes(int id, String nom, String prenom, boolean cloture) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.date = LocalDateTime.now();
		this.cloture = cloture;
	}

	
	public Commandes(String nom, String prenom, boolean cloture) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.date = LocalDateTime.now();
		this.cloture = cloture;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public String getNom() {
		return nom;
	}

	
	public void setNom(String nom) {
		this.nom = nom;
	}

	
	public String getPrenom() {
		return prenom;
	}

	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	
	public LocalDateTime getDate() {
		return date;
	}

	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	
	public boolean isCloture() {
		return cloture;
	}

	
	public void setCloture(boolean cloture) {
		this.cloture = cloture;
	}

	
	@Override
	public String toString() {
		return "Commande [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", date=" + date + ", cloture=" + cloture
				+ "]";
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commandes other = (Commandes) obj;
		return id == other.id;
	}
}