package be.epsmarche.sgbd.tj.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Check;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Hannart Thierry-Julien
 */
@Entity
@Table(name = "articles")
@Check(constraints = "stock_article >= 0")
public class Articles implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_article")
	int id;
	
	@Column(name = "denomination_article", nullable = false, length = 50)
	String denomination;
	
	@Min(value = 0, message = "Le prix doit être un nombre plus grand ou égal à 0.")
	@Column(name = "prix_article", nullable = false)
	double prix;
	
	@Min(value = 0, message = "Le stock doit être un nombre plus grand ou égal à 0.")
	@Column(name = "stock_article", nullable = false)
	int stock;
	
	@Column(name = "actif_article", nullable = false)
	boolean actif;

	@ManyToOne
	@JoinColumn(name = "categorie_article", referencedColumnName = "id_categorie")
	Categories categorie;

	@OneToMany(mappedBy = "article")
	private List<ListesArticle> listefk1;

	
	public Articles() {
	}

	
	public Articles(int id, Categories categorie, String denomination, double prix, int stock, boolean actif) {
		super();
		this.id = id;
		this.categorie = categorie;
		this.denomination = denomination;
		this.prix = prix;
		this.stock = stock;
		this.actif = actif;
	}

	
	public Articles(Categories categorie, String denomination, double prix, int stock, boolean actif) {
		super();
		this.categorie = categorie;
		this.denomination = denomination;
		this.prix = prix;
		this.stock = stock;
		this.actif = actif;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public Categories getCategorie() {
		return categorie;
	}

	
	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	}

	
	public String getDenomination() {
		return denomination;
	}

	
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	
	public double getPrix() {
		return prix;
	}

	
	public void setPrix(double prix) {
		this.prix = prix;
	}

	
	public int getStock() {
		return stock;
	}

	
	public void setStock(int stock) {
		this.stock = stock;
	}

	
	public boolean isActif() {
		return actif;
	}

	
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	
	@Override
	public String toString() {
		return "Article [id=" + id + ", categorie=" + categorie + ", denomination=" + denomination + ", prix=" + prix
				+ ", stock=" + stock + ", actif=" + actif + "]";
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
		Articles other = (Articles) obj;
		return id == other.id;
	}

}
