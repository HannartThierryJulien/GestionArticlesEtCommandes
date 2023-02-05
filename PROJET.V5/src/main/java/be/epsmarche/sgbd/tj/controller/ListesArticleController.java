package be.epsmarche.sgbd.tj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import be.epsmarche.sgbd.tj.model.ListesArticle;
import be.epsmarche.sgbd.tj.service.ListesArticleService;

@Controller
@RequestMapping(value = "/projetv4/listesArticle/")
public class ListesArticleController {

	@Autowired
	ListesArticleService listesArtServ;

	
	@GetMapping("deleteListeArticle/{id}")
	public String deleteListeArticle(@PathVariable("id") int id) {
		ListesArticle listeArticleASupprimer = listesArtServ.getListeArticleById(id);
		listesArtServ.deleteListeArticle(listeArticleASupprimer);
		
		return "redirect:/projetv4/commandes/showCommandeDetaillee/" + listeArticleASupprimer.getCommande().getId();
	}
}