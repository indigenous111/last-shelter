package in.indigenous.last.shelter.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.indigenous.last.shelter.services.global.GlobalHeroService;
import in.indigenous.last.shelter.services.global.GlobalTroopService;

@Controller
@RequestMapping(value = "/global")
public class LastShelterGlobalController {

	@Resource
	private GlobalHeroService globalHeroService;

	@Resource
	private GlobalTroopService globalTroopService;

	@GetMapping("/home")
	public String global(Model model) {
		return "global/global";
	}

	@RequestMapping(value = "/hero/detail", method = RequestMethod.GET)
	public String heroData(Model model) {
		model.addAttribute("heroDetails", globalHeroService.getGlobalHeroDetails());
		return "global/hero-details";
	}

	@RequestMapping(value = "/troops/detail", method = RequestMethod.GET)
	public String troops(Model model) {
		model.addAttribute("troopDetails", globalTroopService.getGlobalTroopDetails());
		return "global/troop-details";
	}

}
