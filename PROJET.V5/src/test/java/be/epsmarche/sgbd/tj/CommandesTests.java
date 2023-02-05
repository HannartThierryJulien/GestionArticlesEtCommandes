package be.epsmarche.sgbd.tj;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.service.CommandesService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommandesTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("\n");
	}

	@Autowired
	private CommandesService comServ;

	static int idCommandeAjouteDsDB;

	@Test
	@Order(1)
	public void testAjouterCommandeDB() {
		System.out.println("######################################################################");
		System.out.println("##                     TEST AJOUTER COMMANDE DB                     ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Commandes commande1 = new Commandes("Hannart", "TJ", false);
		Commandes commandeAjoutee = comServ.addCommande(commande1);
		
		assertThat(commandeAjoutee.getNom()).isEqualTo("Hannart");
		assertThat(commandeAjoutee.getPrenom()).isEqualTo("TJ");
		assertThat(commandeAjoutee.isCloture()).isEqualTo(false);	
		
		idCommandeAjouteDsDB = commandeAjoutee.getId();
	}

	@Test
	@Order(2)
	public void testLireCommandeDB() {
		System.out.println("######################################################################");
		System.out.println("##                      TEST LIRE COMMANDE DB                       ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Commandes commande = comServ.getCommandeById(idCommandeAjouteDsDB);

		assertThat(commande.getNom()).isEqualTo("Hannart");
		assertThat(commande.getPrenom()).isEqualTo("TJ");
		assertThat(commande.isCloture()).isEqualTo(false);
	}

	@Test
	@Order(3)
	public void testUpdateCommandeDB() {
		System.out.println("######################################################################");
		System.out.println("##                       TEST UPDATE COMMANDE                       ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");
		
		Commandes commande1 = new Commandes("Hannart", "TJ", false);
		commande1.setCloture(true);
		comServ.addCommande(commande1);
		
		Commandes commande2 = comServ.getCommandeById(commande1.getId());

		assertThat(commande2.getNom()).isEqualTo("Hannart");
		assertThat(commande2.getPrenom()).isEqualTo("TJ");
		assertThat(commande2.isCloture()).isEqualTo(true);
	}

	@Test
	@Order(4)
	public void testDeleteCommandeDB() {
		System.out.println("######################################################################");
		System.out.println("##                     TEST DELETE COMMANDE DB                      ##");
		System.out.println("##                                                                  ##");
		System.out.println("######################################################################");

		Commandes commande1 = comServ.getCommandeById(idCommandeAjouteDsDB);
		comServ.deleteCommande(commande1);
		
		Commandes commande2 = comServ.getCommandeById(commande1.getId());
		assertThat(commande2).isNull();
	}
	
}
