package be.epsmarche.sgbd.tj.restAPI;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class RestAPIQueries {

	@PersistenceContext
	EntityManager em;

	
	public RestAPIQueries() {
	}

	
	/**
	 * Permet de préparer les données nécessaires aux Google Charts. D'abord une
	 * query précise est lancée, ensuite les données récupérées sont formatées.
	 * 
	 * @param typeDonneesVoulues : correspond au type de graphique désiré
	 * @return
	 */
	public List<List<Object>> getDataForChart(String typeDonneesVoulues) {

		String query = null;
		List<List<Object>> donneesFormatees = new ArrayList<>();
		List<Object> header = new ArrayList<Object>();

		switch (typeDonneesVoulues) {
		case "barchart_NbrCommandesParCategories":
			query = "SELECT c.nom as categorie, count(la.id) as nb_commande " + "FROM ListesArticle la "
					+ "INNER JOIN Articles a on a.id = la.article " + "INNER JOIN Categories c on c.id = a.categorie "
					+ "GROUP BY c.nom ";
			header.add("Categories");
			header.add("Commandes");
			break;
			
		case "piechart_NbrArticlesPris":
			query = "SELECT a.denomination as Articles, SUM(la.quantite) as nb_commande " + "FROM ListesArticle la "
					+ "INNER JOIN Articles a on a.id = la.article " + "GROUP BY a.denomination ";
			header.add("Articles");
			header.add("Nombre de fois commandés");
			break;
			
		case "linechart_EvolutionNbrCommandesPassees":
			query = "SELECT date_format(date, '%Y-%m') as date, count(id) as nombre_commandes " + " FROM Commandes "
					+ " GROUP BY date_format(date, '%Y-%m') " + " ORDER BY date ";
			header.add("Date");
			header.add("Nbr commandes");
			break;
			
		default:
			/* ***************** */
		}

		@SuppressWarnings("unchecked")
		List<Object[]> resultatQuery = em.createQuery(query).getResultList();

		donneesFormatees.add(header);

		for (Object[] obj : resultatQuery) {
			List<Object> donnees = new ArrayList<>();
			donnees.add(obj[0].toString());
			donnees.add(obj[1]);
			donneesFormatees.add(donnees);
		}

		return donneesFormatees;
	}
}
