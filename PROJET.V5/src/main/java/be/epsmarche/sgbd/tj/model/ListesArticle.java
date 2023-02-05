package be.epsmarche.sgbd.tj.model;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Min;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Hannart Thierry-Julien
 */
@Entity
@Table(name = "ListesArticle")
public class ListesArticle implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_listeArticles")
	int id;
	
	@Min(value = 0, message = "Le stock doit être un nombre plus grand ou égal à 0.")
	@Column(name = "qtyCommande", nullable = false)
	int quantite;
	
	@Column(name = "prixActuel", nullable = false)
	double prixActuel;

	@ManyToOne
	@JoinColumn(name = "fk_commande", referencedColumnName = "id_commande")
	private Commandes commande;

	@ManyToOne
	@JoinColumn(name = "fk_article", referencedColumnName = "id_article")
	private Articles article;

	
	public ListesArticle() {
	}

	
	public ListesArticle(int quantite, double prixActuel, Commandes commande, Articles article) {
		super();
		this.quantite = quantite;
		this.prixActuel = prixActuel;
		this.commande = commande;
		this.article = article;
	}

	
	public int getQuantite() {
		return quantite;
	}

	
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	
	public double getPrixActuel() {
		return prixActuel;
	}

	
	public void setPrixActuel(double prixActuel) {
		this.prixActuel = prixActuel;
	}

	
	public Commandes getCommande() {
		return commande;
	}

	
	public void setCommande(Commandes commande) {
		this.commande = commande;
	}

	
	public Articles getArticle() {
		return article;
	}

	
	public void setArticle(Articles article) {
		this.article = article;
	}

	
	public int getId() {
		return id;
	}

	
	@Override
	public String toString() {
		return "ListeArticles [id=" + id + ", quantite=" + quantite + ", prixActuel=" + prixActuel + ", commande="
				+ commande.getId() + ", article=" + article.getId() + "]";
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
		ListesArticle other = (ListesArticle) obj;
		return id == other.id;
	}
}
