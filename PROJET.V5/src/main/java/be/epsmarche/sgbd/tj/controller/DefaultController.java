package be.epsmarche.sgbd.tj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
	
	/**
	 * Permet de rediriger vers la page home.html dès le lancement du projet.
	 * @return
	 */
	@GetMapping("/")
	public String redirectToHome() {
		return "redirect:/projetv4/home";
	}
	
	@GetMapping("/projetv4/home")
	public String showHome(Model model) {
		return "home";
	}
}