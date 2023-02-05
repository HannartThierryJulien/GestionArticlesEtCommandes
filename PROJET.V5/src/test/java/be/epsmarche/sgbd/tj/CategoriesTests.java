package be.epsmarche.sgbd.tj;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import be.epsmarche.sgbd.tj.model.Categories;
import be.epsmarche.sgbd.tj.service.CategoriesService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriesTests {
	
	@Autowired
	private CategoriesService catServ;
	
	static int idCategorieAjouteeDsDB;

	@BeforeAll
	public static void setUpClass() {
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
	public void testAjouterCategorieDB() {
		System.out.println("######################################################################");
		System.out.println("##                    TEST AJOUTER CATEGORIE DB                     ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Categories categorie = new Categories("Légumes", true);
		Categories categorieAjoutee = catServ.addCategorie(categorie);

		assertThat(categorieAjoutee.getNom()).isEqualTo("Légumes");
		assertThat(categorieAjoutee.isActif()).isEqualTo(true);
		
		idCategorieAjouteeDsDB = categorieAjoutee.getId();
	}

	@Test
	@Order(2)
	public void testLireCategorieDB() {
		System.out.println("######################################################################");
		System.out.println("##                      TEST LIRE CATEGORIE DB                      ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");
		
		Categories categorie = catServ.getCategorieById(idCategorieAjouteeDsDB);

		assertThat(categorie.getNom()).isEqualTo("Légumes");
		assertThat(categorie.isActif()).isEqualTo(true);
	}

	@Test
	@Order(3)
	public void testUpdateCategorieDB() {
		System.out.println("######################################################################");
		System.out.println("##                     TEST UPDATE CATEGORIE DB                     ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");
		
		Categories categorie1 = catServ.getCategorieById(idCategorieAjouteeDsDB);
		categorie1.setActif(false);
		catServ.updateCategorie(idCategorieAjouteeDsDB, categorie1.getNom(), categorie1.isActif());
		
		Categories categorie2 = catServ.getCategorieById(idCategorieAjouteeDsDB);
		
		assertThat(categorie2.getNom()).isEqualTo("Légumes");
		assertThat(categorie2.isActif()).isEqualTo(false);
	}


	@Test
	@Order(4)
	public void testDeleteCategorieDB() {
		System.out.println("######################################################################");
		System.out.println("##                     TEST DELETE CATEGORIE DB                     ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");
		
		Categories categorie1 = catServ.getCategorieById(idCategorieAjouteeDsDB);
		catServ.deleteCategorie(categorie1);
		
		Categories categorie2 = catServ.getCategorieById(categorie1.getId());
		assertThat(categorie2).isNull();
	}
	
}
