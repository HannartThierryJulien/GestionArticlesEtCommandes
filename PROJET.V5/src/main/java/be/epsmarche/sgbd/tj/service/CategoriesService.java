package be.epsmarche.sgbd.tj.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.epsmarche.sgbd.tj.model.Categories;
import be.epsmarche.sgbd.tj.repository.CategoriesRepository;

@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository catRep;

	
	public CategoriesService() {
	}

	
	public Categories getCategorieById(int id) {
		return catRep.findById(id);
	}

	
	public ArrayList<Categories> getCategories() {
		return (ArrayList<Categories>) catRep.findAll();
	}

	
	public ArrayList<Categories> getCategoriesByActif(boolean actif) {
		return (ArrayList<Categories>) catRep.findAllByActif(actif);
	}

	
	public Categories addCategorie(Categories categorie) {
		Categories cat = catRep.save(categorie);
		return cat;
	}

	
	public Categories updateCategorie(int id, String nom, boolean actif) {
		Categories categorie = catRep.findById(id);
		categorie.setNom(nom);
		categorie.setActif(actif);
		return catRep.save(categorie);
	}

	
	public void deleteCategorie(Categories categorie) {
		catRep.delete(categorie);
	}
}
