package be.epsmarche.sgbd.tj;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.model.Categories;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.model.ListesArticle;
import be.epsmarche.sgbd.tj.service.ArticlesService;
import be.epsmarche.sgbd.tj.service.CategoriesService;
import be.epsmarche.sgbd.tj.service.CommandesService;
import be.epsmarche.sgbd.tj.service.ListesArticleService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS) /* annotation à mettre car @BeforeAll n'est pas en static */
class ListesArticleTests {

	@Autowired
	private ArticlesService artServ;

	@Autowired
	private CommandesService comServ;

	@Autowired
	private ListesArticleService listeArtServ;
	
	@Autowired
	private CategoriesService catServ;

	static Categories categorie = new Categories("Legumes", true);
	static Articles article = new Articles(categorie, "Carotte", 1.2, 20, true);
	static Commandes commande = new Commandes("Hannart", "TJ", false);
	static int idListeArticleAjouteeDsDB;

	@BeforeAll
	public /* static */ void setUpClass() {
		catServ.addCategorie(categorie);
		artServ.addArticle(article);
		comServ.addCommande(commande);
	}

	@AfterAll
	public static void tearDownClass() {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("\n");
	}

	@Test
	@Order(1)
	public void testAjouterListeArticlesDB() {
		System.out.println("######################################################################");
		System.out.println("##                  TEST AJOUTER LISTE ARTICLES DB                  ##");
		System.out.println("##             et update du stock de l'article commandé             ##");
		System.out.println("######################################################################");

		ListesArticle listesArticle = new ListesArticle(2, 10, commande, article);
		ListesArticle listesArticleAjoutee = listeArtServ.addListeArticle(listesArticle);

		assertThat(listesArticleAjoutee.getQuantite()).isEqualTo(2);
		assertThat(listesArticleAjoutee.getPrixActuel()).isEqualTo(10);
		assertThat(listesArticleAjoutee.getCommande().getId()).isEqualTo(commande.getId());
		assertThat(listesArticleAjoutee.getArticle().getId()).isEqualTo(article.getId());

		idListeArticleAjouteeDsDB = listesArticleAjoutee.getId();

		assertThat(artServ.getArticleById(article.getId()).getStock()).isEqualTo(18);
	}

	@Test
	@Order(2)
	public void testLireListeArticlesDB() {
		System.out.println("######################################################################");
		System.out.println("##                       TEST LIRE ARTICLE DB                       ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		ListesArticle listesArticle = listeArtServ.getListeArticleById(idListeArticleAjouteeDsDB);

		assertThat(listesArticle.getQuantite()).isEqualTo(2);
		assertThat(listesArticle.getPrixActuel()).isEqualTo(10);
		assertThat(listesArticle.getCommande().getId()).isEqualTo(commande.getId());
		assertThat(listesArticle.getArticle().getId()).isEqualTo(article.getId());
	}

	@Test
	@Order(3)
	public void testUpdateListeArticlesDB() {
		System.out.println("######################################################################");
		System.out.println("##                    TEST UPDATE LISTE ARTICLES                    ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		ListesArticle listesArticle1 = listeArtServ.getListeArticleById(idListeArticleAjouteeDsDB);
		listesArticle1.setPrixActuel(15);
		listeArtServ.updateListeArticle(listesArticle1);

		ListesArticle listesArticle2 = listeArtServ.getListeArticleById(listesArticle1.getId());

		assertThat(listesArticle2.getQuantite()).isEqualTo(2);
		assertThat(listesArticle2.getPrixActuel()).isEqualTo(15);
		assertThat(listesArticle2.getCommande().getId()).isEqualTo(commande.getId());
		assertThat(listesArticle2.getArticle().getId()).isEqualTo(article.getId());

	}

	@Test
	@Order(4)
	public void testUpdateStockListeArticlesDB() {
		System.out.println("######################################################################");
		System.out.println("##                 TEST UPDATE STOCK LISTE ARTICLES                 ##");
		System.out.println("##             et update du stock de l'article commandé             ##");
		System.out.println("######################################################################");

		ListesArticle listesArticle1 = listeArtServ.getListeArticleById(idListeArticleAjouteeDsDB);
		listesArticle1.setQuantite(9);
		listeArtServ.updateListeArticle(listesArticle1);

		ListesArticle listesArticle2 = listeArtServ.getListeArticleById(listesArticle1.getId());

		assertThat(listesArticle2.getQuantite()).isEqualTo(9);
		assertThat(listesArticle2.getPrixActuel()).isEqualTo(15);
		assertThat(listesArticle2.getCommande().getId()).isEqualTo(commande.getId());
		assertThat(listesArticle2.getArticle().getId()).isEqualTo(article.getId());
		
		assertThat(artServ.getArticleById(article.getId()).getStock()).isEqualTo(11);
	}

	@Test
	@Order(5)
	public void testDeleteListeArticlesDB() {
		System.out.println("######################################################################");
		System.out.println("##                    TEST DELETE LISTE ARTICLES                    ##");
		System.out.println("##             et update du stock de l'article commandé             ##");
		System.out.println("######################################################################");

		ListesArticle listesArticle1 = listeArtServ.getListeArticleById(idListeArticleAjouteeDsDB);
		listeArtServ.deleteListeArticle(listesArticle1);

		ListesArticle listesArticle2 = listeArtServ.getListeArticleById(listesArticle1.getId());
		assertThat(listesArticle2).isNull();

		assertThat(20).isEqualTo(artServ.getArticleById(article.getId()).getStock());
	}
}
