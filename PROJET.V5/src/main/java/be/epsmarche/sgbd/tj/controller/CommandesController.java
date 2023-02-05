package be.epsmarche.sgbd.tj.controller;

import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.model.Commandes;
import be.epsmarche.sgbd.tj.model.ListesArticle;
import be.epsmarche.sgbd.tj.service.ArticlesService;
import be.epsmarche.sgbd.tj.service.CommandesService;
import be.epsmarche.sgbd.tj.service.ListesArticleService;

@Controller
@RequestMapping(value = "/projetv4/commandes/")
public class CommandesController {

	@Autowired
	ArticlesService artServ;

	
	@Autowired
	CommandesService comServ;

	
	@Autowired
	ListesArticleService listesArtServ;

	
	@GetMapping("showCommandes")
	public String showCommandes(Model model) {
		model.addAttribute("commandes", comServ.getCommandes());
		return "commandes/showCommandes";
	}

	
	@GetMapping("formAddCommande")
	public String afficherFormulaireAjoutCommande(Commandes commande, Model model) {
		return "commandes/addCommande";
	}

	
	@PostMapping("addCommande")
	public String ajouterCommande(@Valid Commandes commandeAAjouter, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "formAddCommande";
		}
		comServ.addCommande(commandeAAjouter);
		return "redirect:/projetv4/commandes/showCommandeDetaillee/" + commandeAAjouter.getId();
	}

	
	@GetMapping("/formModifyCommande/{id}")
	public String afficherFormulaireModificationCommande(@PathVariable("id") int id, Model model) {
		Commandes commandeAModifier = comServ.getCommandeById(id);
		model.addAttribute("commandeAModifier", commandeAModifier);
		return "commandes/updateCommande";
	}

	
	@PostMapping("/updateCommande/{id}")
	public String modifierCommande(@PathVariable("id") int id, @Valid Commandes commandeAModifier, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "/formModifyCommande/{id}";
		}
		comServ.updateCommande(commandeAModifier);
		return "redirect:/projetv4/commandes/showCommandeDetaillee/{id}";
	}

	
	@GetMapping("deleteCommande/{id}")
	public String deleteCommande(@PathVariable("id") int id) {
		Commandes commandeASupprimer = comServ.getCommandeById(id);
		comServ.deleteCommande(commandeASupprimer);
		return "redirect:/projetv4/commandes/showCommandes";
	}

	
	@GetMapping("/showCommandeDetaillee/{id}")
	public String afficherCommandeDetaillee(@PathVariable("id") int id, Model model) {
		Commandes commande = comServ.getCommandeById(id);
		model.addAttribute("commande", commande);
		ArrayList<ListesArticle> articlesDeLaCommande = listesArtServ.getAllListesArticleByCommande(commande);
		model.addAttribute("articlesDeLaCommande", articlesDeLaCommande);
		double prixTotalCommande = comServ.getPrixTotalCommande(commande);
		model.addAttribute("prixTotalCommande", prixTotalCommande);
		return "commandes/showDetailCommande";
	}

	
	@GetMapping("/showArticlesCommandables/{commandeId}")
	public String showArticlesCommandables(@PathVariable("commandeId") int commandeId, Model model) {
		model.addAttribute("commandeId", commandeId);
		model.addAttribute("articlesCommandables", artServ.getArticlesByActif(false));
		return "commandes/listesArticle/showArticlesCommandables";
	}

	
	@GetMapping("/formDiminuerQtyArticleCommande/{listeArticleId}")
	public String showDiminuerQtyArticle(@PathVariable("listeArticleId") int listeArticleId, Model model) {
		ListesArticle listeArticleADiminuer = listesArtServ.getListeArticleById(listeArticleId);
		model.addAttribute("listeArticleADiminuer", listeArticleADiminuer);
		return "commandes/listesArticle/diminuerQtyArticleCommande";
	}

	
	@PostMapping("/diminuerQtyArticleCommande/{listeAticleId}")
	public String diminuerQtyArticleCommande(@PathVariable("listeAticleId") int listeAticleId,
			@RequestParam("quantite") int quantite, Model model) {
		ListesArticle listeArticle = listesArtServ.getListeArticleById(listeAticleId);

		Articles article = artServ.getArticleById(listeArticle.getArticle().getId());
		article.setStock(article.getStock() + (listeArticle.getQuantite() - quantite));
		artServ.updateArticle(article);

		listeArticle.setQuantite(quantite);
		listesArtServ.updateListeArticle(listeArticle);

		return "redirect:/projetv4/commandes/showCommandeDetaillee/" + listeArticle.getCommande().getId();
	}

	
	@GetMapping("/formCommanderArticle/{commandeId}/{articleId}")
	public String showCommanderArticle(@PathVariable("commandeId") int commandeId,
			@PathVariable("articleId") int articleId, Model model) {
		Articles article = artServ.getArticleById(articleId);
		model.addAttribute("commandeId", commandeId);
		model.addAttribute("article", article);
		return "commandes/listesArticle/commanderArticle";
	}

	
	/**
	 * Méthode servant à commander un article pour une commande existante. Avant la
	 * création de la listeArticle, une vérification est nécessaire afin de savoir
	 * si le stock de l'article est suffisant.
	 * 
	 * @param commandeId
	 * @param articleId
	 * @param quantite
	 * @param model
	 * @return vers la page affichant la commande détaillée
	 */
	@PostMapping("/commanderArticle/{commandeId}/{articleId}")
	public String commanderArticle(@PathVariable("commandeId") int commandeId, @PathVariable("articleId") int articleId,
			@RequestParam("quantite") int quantite, Model model) {
		Commandes commande = comServ.getCommandeById(commandeId);
		Articles article = artServ.getArticleById(articleId);

		if (quantite > article.getStock()) {
			model.addAttribute("errorMessage",
					"La quantité d'articles commandés ne peut pas être supérieure à la quantité en stock.");
			model.addAttribute("commande", commande);
			model.addAttribute("article", article);
			return "commandes/formCommanderArticle";
		} else {
			ListesArticle listeArticle = new ListesArticle(quantite, article.getPrix(), commande, article);
			listesArtServ.addListeArticle(listeArticle);
			return "redirect:/projetv4/commandes/showCommandeDetaillee/{commandeId}";
		}
	}
}