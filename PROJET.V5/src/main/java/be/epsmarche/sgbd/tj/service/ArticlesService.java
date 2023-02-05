package be.epsmarche.sgbd.tj.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.epsmarche.sgbd.tj.model.Articles;
import be.epsmarche.sgbd.tj.repository.ArticlesRepository;

@Service
public class ArticlesService {

	@Autowired
	private ArticlesRepository artRep;

	
	public ArticlesService() {
	}

	
	public Articles getArticleById(int id) {
		return artRep.findById(id);
	}

	
	public ArrayList<Articles> getArticles() {
		return (ArrayList<Articles>) artRep.findAll();
	}

	
	public ArrayList<Articles> getArticlesByActif(boolean actif) {
		return (ArrayList<Articles>) artRep.findAllByActif(true);
	}

	
	public Articles addArticle(Articles article) {
		Articles art = artRep.save(article);
		return art;
	}

	
	public Articles updateArticle(Articles article) {
		Articles art = artRep.save(article);
		return art;
	}

	
	public void deleteArticle(Articles article) {
		artRep.delete(article);
	}
}
