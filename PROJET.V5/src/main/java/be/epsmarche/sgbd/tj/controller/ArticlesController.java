package be.epsmarche.sgbd.tj.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.service.ArticlesService;
import be.epsmarche.sgbd.tj.service.CategoriesService;

@Controller
@RequestMapping(value = "/projetv4/articles/")
public class ArticlesController {

	@Autowired
	ArticlesService artServ;

	
	@Autowired
	CategoriesService catServ;

	
	@GetMapping("showArticles")
	public String afficherArticles(Model model) {
		model.addAttribute("showArticles", artServ.getArticles());
		return "articles/showArticles";
	}

	
	@GetMapping("formAddArticle")
	public String afficherFormulaireAjoutArticle(Articles article, Model model) {
		model.addAttribute("categories", catServ.getCategoriesByActif(true));
		return "articles/addArticle";
	}

	
	@PostMapping("addArticle")
	public String ajouterArticle(@Valid Articles article, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "articles/formAddArticle";
		}
		artServ.addArticle(article);
		return "redirect:/projetv4/articles/showArticles";
	}

	
	@GetMapping("/formModifyArticle/{id}")
	public String afficherFormulaireModificationArticle(@PathVariable("id") int id, Model model) {
		Articles articleAModifier = artServ.getArticleById(id);
		model.addAttribute("categories", catServ.getCategoriesByActif(true));
		model.addAttribute("articleAModifier", articleAModifier);
		return "articles/updateArticle";
	}

	
	@PostMapping("/updateArticle/{id}")
	public String modifierArticle(@PathVariable("id") int id, @Valid Articles articleAModifier, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "articles/formModifyArticle/{id}";
		}
		artServ.updateArticle(articleAModifier);
		return "redirect:/projetv4/articles/showArticles";
	}

	
	@GetMapping("deleteArticle/{id}")
	public String supprimerArticle(@PathVariable("id") int id) {
		Articles articleASupprimer = artServ.getArticleById(id);

		try {
			artServ.deleteArticle(articleASupprimer);
		} catch (Exception e) {
			return "erreurs/erreurDeleteArticle";
		}
		
		return "redirect:/projetv4/articles/showArticles";
	}
}