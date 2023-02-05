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
import be.epsmarche.sgbd.tj.service.ArticlesService;
import be.epsmarche.sgbd.tj.service.CategoriesService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS) /* annotation à mettre car @BeforeAll n'est pas en static */
class ArticlesTests {

	@Autowired
	private ArticlesService artServ;
	
	@Autowired
	private CategoriesService catServ;

	static int idArticleAjouteDsDB;
	static Categories categorie = new Categories("Legumes", true);

	@BeforeAll
	public /* static */ void setUpClass() {
		catServ.addCategorie(categorie);
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
	public void testAjouterArticleDB() {
		System.out.println("######################################################################");
		System.out.println("##                     TEST AJOUTER ARTICLE DB                      ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		
		
		Articles article1 = new Articles(categorie, "Aubergine", 32.5, 10, true);
		Articles articleAjoute = artServ.addArticle(article1);

		assertThat(articleAjoute.getCategorie().getNom()).isEqualTo(categorie.getNom());
		assertThat(articleAjoute.getCategorie().isActif()).isEqualTo(categorie.isActif());
		assertThat(articleAjoute.getDenomination()).isEqualTo("Aubergine");
		assertThat(articleAjoute.getPrix()).isEqualTo(32.5);
		assertThat(articleAjoute.getStock()).isEqualTo(10);
		assertThat(articleAjoute.isActif()).isEqualTo(true);
		
		idArticleAjouteDsDB = articleAjoute.getId();
	}

	@Test
	@Order(2)
	public void testLireArticleDB() {
		System.out.println("######################################################################");
		System.out.println("##                       TEST LIRE ARTICLE DB                       ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Articles article = artServ.getArticleById(idArticleAjouteDsDB);

		assertThat(article.getCategorie().getNom()).isEqualTo(categorie.getNom());
		assertThat(article.getCategorie().isActif()).isEqualTo(categorie.isActif());
		assertThat(article.getDenomination()).isEqualTo("Aubergine");
		assertThat(article.getPrix()).isEqualTo(32.5);
		assertThat(article.getStock()).isEqualTo(10);
		assertThat(article.isActif()).isEqualTo(true);
	}

	@Test
	@Order(3)
	public void testUpdateArticleDB() {
		System.out.println("######################################################################");
		System.out.println("##                      TEST UPDATE ARTICLE DB                      ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");
		
		Articles article1 = artServ.getArticleById(idArticleAjouteDsDB);
		article1.setPrix(35);
		artServ.addArticle(article1);
		
		Articles article2 = artServ.getArticleById(article1.getId());

		assertThat(article2.getCategorie().getNom()).isEqualTo(categorie.getNom());
		assertThat(article2.getCategorie().isActif()).isEqualTo(categorie.isActif());
		assertThat(article2.getDenomination()).isEqualTo("Aubergine");
		assertThat(article2.getPrix()).isEqualTo(35);
		assertThat(article2.getStock()).isEqualTo(10);
		assertThat(article2.isActif()).isEqualTo(true);
	}


	@Test
	@Order(4)
	public void testDeleteArticleDB() {
		System.out.println("######################################################################");
		System.out.println("##                      TEST DELETE ARTICLE DB                      ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Articles article1 = artServ.getArticleById(idArticleAjouteDsDB);
		artServ.deleteArticle(article1);
		
		Articles article2 = artServ.getArticleById(article1.getId());
		assertThat(article2).isNull();
	}

}
