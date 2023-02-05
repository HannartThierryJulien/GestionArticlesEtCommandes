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

import be.epsmarche.sgbd.tj.model.Categories;
import be.epsmarche.sgbd.tj.service.CategoriesService;

@Controller
@RequestMapping(value = "/projetv4/categories/")
public class CategoriesController {

	@Autowired
	CategoriesService catServ;

	
	@GetMapping("showCategories")
	public String showCategories(Model model) {
		model.addAttribute("categories", catServ.getCategories());
		return "categories/showCategories";
	}

	
	@GetMapping("formAddCategorie")
	public String afficherFormulaireAjoutCategorie(Categories categorie, Model model) {
		return "categories/addCategorie";
	}

	
	@PostMapping("addCategorie")
	public String ajouterCategorie(@Valid Categories categorieAAjouter, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "formAddCategorie";
		}
		catServ.addCategorie(categorieAAjouter);
		return "redirect:/projetv4/categories/showCategories";
	}

	
	@GetMapping("/formModifyCategorie/{id}")
	public String afficherFormulaireModificationCategorie(@PathVariable("id") int id, Model model) {
		Categories categorieAModifier = catServ.getCategorieById(id);
		model.addAttribute("categorieAModifier", categorieAModifier);
		return "categories/updateCategorie";
	}

	
	@PostMapping("/updateCategorie/{id}")
	public String modifierCategorie(@PathVariable("id") int id, @Valid Categories categorieAModifier,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/formModifyCategorie/{id}";
		}
		catServ.updateCategorie(id, categorieAModifier.getNom(), categorieAModifier.isActif());
		return "redirect:/projetv4/categories/showCategories";
	}

	
	@GetMapping("deleteCategorie/{id}")
	public String deleteCategorie(@PathVariable("id") int id) {
		Categories categorieASupprimer = catServ.getCategorieById(id);

		try {
			catServ.deleteCategorie(categorieASupprimer);
		} catch (Exception e) {
			return "erreurs/erreurDeleteCategorie";
		}
		
		return "redirect:/projetv4/categories/showCategories";
	}
}