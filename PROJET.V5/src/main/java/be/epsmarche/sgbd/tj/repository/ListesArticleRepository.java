package be.epsmarche.sgbd.tj.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.model.ListesArticle;

public interface ListesArticleRepository extends JpaRepository<ListesArticle, Integer> {

	public ListesArticle findById(int id);

	public ListesArticle findByCommande(Commandes commande);
	
	public ListesArticle findByCommandeAndArticleAndPrixActuel(Commandes commande, Articles article, double prixActuel);

	ArrayList<ListesArticle> findAllByCommande(Commandes commande);

}