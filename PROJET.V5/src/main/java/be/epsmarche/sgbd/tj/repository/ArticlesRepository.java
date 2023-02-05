package be.epsmarche.sgbd.tj.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import be.epsmarche.sgbd.tj.model.Articles;

public interface ArticlesRepository extends JpaRepository<Articles, Integer> {

	public Articles findById(int id);

	ArrayList<Articles> findAllByActif(boolean actif);

}