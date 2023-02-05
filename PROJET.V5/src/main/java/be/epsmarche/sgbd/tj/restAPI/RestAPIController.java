package be.epsmarche.sgbd.tj.restAPI;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/projetv4/restAPI/")
public class RestAPIController {

	@Autowired
	RestAPIQueries statQ;

	
	@GetMapping("showGraphNbrCommandesParCategories")
	public String showGraphNbrCommandesParCategories(Model model) {
		return "graphiques/graphNbrCommandesParCategories";
	}

	
	@GetMapping("getDataNbrCommandesParCategories")
	@ResponseBody
	public List<List<Object>> getDataNbrCommandesParCategories() {
		List<List<Object>> list = statQ.getDataForChart("barchart_NbrCommandesParCategories");
		return list;
	}

	
	@GetMapping("showGraphNbrArticlesPris")
	public String showGraphNbrArticlesPris(Model model) {
		return "graphiques/graphNbrArticlesPris";
	}

	
	@GetMapping("getDataNbrArticlesPris")
	@ResponseBody
	public List<List<Object>> getDataNbrArticlesPris() {
		List<List<Object>> list = statQ.getDataForChart("piechart_NbrArticlesPris");
		return list;
	}

	
	@GetMapping("showGraphEvolutionNbrCommandesPassees")
	public String showGraphEvolutionNbrCommandesPassees(Model model) {
		return "graphiques/graphEvolutionNbrCommandesPassees";
	}

	
	@GetMapping("getDataEvolutionNbrCommandesPassees")
	@ResponseBody
	public List<List<Object>> getDataEvolutionNbrCommandesPassees() {
		List<List<Object>> list = statQ.getDataForChart("linechart_EvolutionNbrCommandesPassees");
		return list;
	}
}
