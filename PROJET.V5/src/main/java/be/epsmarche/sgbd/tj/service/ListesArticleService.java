package be.epsmarche.sgbd.tj.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.model.ListesArticle;
import be.epsmarche.sgbd.tj.repository.ListesArticleRepository;
import jakarta.transaction.Transactional;

@Service
public class ListesArticleService {

	@Autowired
	private ListesArticleRepository listRep;

	
	@Autowired
	private ArticlesService artServ;

	
	public ListesArticleService() {
	}

	
	public ListesArticle getListeArticleById(int id) {
		return listRep.findById(id);
	}

	
	public ListesArticle getListeArticleByCommandeAndByArticle(Commandes commande, Articles article,
			double prixActuel) {
		return listRep.findByCommandeAndArticleAndPrixActuel(commande, article, prixActuel);
	}

	
	public ArrayList<ListesArticle> getAllListesArticleByCommande(Commandes commande) {
		return listRep.findAllByCommande(commande);
	}

	
	/**
	 * Permet l'ajout d'une listeArticle dans la base de données. Le stock a été
	 * préalablement vérifié lors du lancement de la méthode "commanderArticle" de
	 * la class CommandesController.
	 * 
	 * @param listeArticle
	 * @return
	 */
	@Transactional
	public ListesArticle addListeArticle(ListesArticle listeArticle) {

		// Mise à jour du stock de l'article commandé
		Articles article = artServ.getArticleById(listeArticle.getArticle().getId());
		article.setStock(article.getStock() - listeArticle.getQuantite());
		artServ.updateArticle(article);

		// Recherche d'une listeArticle ayant la même commande et le même article que la
		// listeArticle reçue en paramètre
		ListesArticle listeArtRecherchee = getListeArticleByCommandeAndByArticle(listeArticle.getCommande(),
				listeArticle.getArticle(), listeArticle.getPrixActuel());
		ListesArticle listeArt = null;

		// Si aucune listeArticle n'a été trouvée, alors la listeArticle passée en paramètre peut être ajouter à la base de données
		if (listeArtRecherchee == null) {
			listeArt = listRep.save(listeArticle);

		// Si une listeArticle correspondante a été trouvée, alors on vérifie si leur prix sont identiques.
		// Si c'est le cas, la quantité de la listeArticle existante est mise à jour.
		} else if (listeArtRecherchee.getPrixActuel() == listeArticle.getPrixActuel()) {
			listeArtRecherchee.setQuantite(listeArtRecherchee.getQuantite() + listeArticle.getQuantite());
			listeArt = updateListeArticle(listeArtRecherchee);
			
		// Si une listeArticle correspondante a été trouvée, mais que les prix sont différents
		// alors la listeArticle passée en paramètre peut être ajoutée à la base de données
		} else {
			listeArt = listRep.save(listeArticle);
		}

		return listeArt;
	}

	
	/**
	 * 
	 * @param listeArticle
	 * @return
	 */
	public ListesArticle updateListeArticle(ListesArticle listeArticle) {

		// Récupère la quantité de l'article avant la modification de la listeArticle
		int ancienneQuantite = listRep.findById(listeArticle.getId()).getQuantite();

		// Calcule la différence entre la quantité de la listeArticle avant et après
		// la modification
		int diffQuantite = ancienneQuantite - listeArticle.getQuantite();

		try {
			// Utilise la méthode updateStock pour mettre à jour le stock de l'article
			// associé à la listeArticle
			updateStock(listeArticle.getArticle().getId(), diffQuantite);

			ListesArticle listeArt = listRep.save(listeArticle);
			return listeArt;

		} catch (IllegalArgumentException e) {
			// Si la méthode updateStock lève une exception, null est retourné pour
			// indiquer que la mise à jour de la listeArticle a échouée
			return null;
		}
	}

	
	/**
	 * Permet de supprimer une listeArticle. Avant ça, le stock des articles
	 * commandés est renfloué grâce à la méthode updateStock
	 * 
	 * @param listeArticle
	 */
	public void deleteListeArticle(ListesArticle listeArticle) {
		updateStock(listeArticle.getArticle().getId(), listeArticle.getQuantite());

		listRep.delete(listeArticle);
	}

	
	/**
	 * Méthode permettant de mettre à jour le stock si ce dernier est suffisant.
	 * S'il ne l'est pas, une exception est levée.
	 * 
	 * @param idArticle
	 * @param quantity
	 */
	public void updateStock(int idArticle, int quantity) {
		Articles article = artServ.getArticleById(idArticle);

		// Vérifie que la quantité de l'article est suffisante pour mettre à jour le
		// stock
		if (article.getStock() + quantity >= 0) {

			// Met à jour le stock de l'article en fonction de la quantité passée en
			// paramètre
			article.setStock(article.getStock() + quantity);
			artServ.updateArticle(article);

		// Si la quantité de l'article est négative, levée d'une exception
		} else {
			throw new IllegalArgumentException("La quantité de l'article ne peut pas être négative");
		}
	}
}
