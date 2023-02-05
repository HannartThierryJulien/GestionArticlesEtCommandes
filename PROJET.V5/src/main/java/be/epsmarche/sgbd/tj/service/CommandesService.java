package be.epsmarche.sgbd.tj.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.model.ListesArticle;
import be.epsmarche.sgbd.tj.repository.CommandesRepository;

@Service
public class CommandesService {

	@Autowired
	private CommandesRepository comRep;

	
	@Autowired
	private ListesArticleService listeArtServ;

	
	public CommandesService() {
	}

	
	public Commandes getCommandeById(int id) {
		return comRep.findById(id);
	}

	
	public ArrayList<Commandes> getCommandes() {
		return (ArrayList<Commandes>) comRep.findAll();
	}

	
	/**
	 * Permet l'ajout d'une commande dans la base de données, après que la date ait
	 * été formatée.
	 * 
	 * @param commande
	 * @return
	 */
	public Commandes addCommande(Commandes commande) {
		commande.setDate(LocalDateTime.now().withNano(0).withSecond(0));
		Commandes com = comRep.save(commande);
		return com;
	}

	
	public Commandes updateCommande(Commandes commande) {
		Commandes com = comRep.save(commande);
		return com;
	}

	
	/**
	 * Permet la suppression d'une commande. La suppression n'est lancée que lorsque
	 * toutes ses listesArticle ont été supprimées.
	 * 
	 * @param commande
	 */
	public void deleteCommande(Commandes commande) {

		// Recherche et suppression des listesArticle comprenant cette commande
		ArrayList<ListesArticle> listesArt = listeArtServ.getAllListesArticleByCommande(commande);
		for (ListesArticle listeArt : listesArt) {
			if (listeArt != null) {
				listeArtServ.deleteListeArticle(listeArt);
			}
		}

		comRep.delete(commande);
	}
	
	
	/**
	 * Permet de calculer le prix total d'une commande.
	 * Méthode utilisée pour le model de la page showDetailCommande.html.
	 * @param commandes
	 * @return
	 */
	public double getPrixTotalCommande(Commandes commandes) {
		ArrayList<ListesArticle> listesArt = listeArtServ.getAllListesArticleByCommande(commandes);
		
		double prixTotal = 0;
		for (ListesArticle listeArt : listesArt) {
			prixTotal += listeArt.getPrixActuel()*listeArt.getQuantite();
		}
		
		return prixTotal;
	}
}
