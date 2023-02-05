package be.epsmarche.sgbd.tj.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import be.epsmarche.sgbd.tj.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

	public Categories findById(int id);

	ArrayList<Categories> findAllByActif(boolean actif);

}