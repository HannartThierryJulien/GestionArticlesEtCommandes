package be.epsmarche.sgbd.tj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import be.epsmarche.sgbd.tj.model.Commandes;

public interface CommandesRepository extends JpaRepository<Commandes, Integer> {

	public Commandes findById(int id);

}