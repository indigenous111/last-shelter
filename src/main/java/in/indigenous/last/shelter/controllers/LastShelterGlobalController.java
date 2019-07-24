package in.indigenous.last.shelter.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.indigenous.last.shelter.services.GlobalHeroDataService;

@Controller
@RequestMapping(value="/global")
public class LastShelterGlobalController {
	
	@Resource
	private GlobalHeroDataService globalHeroDataService;
	
	@GetMapping("/home")
	public String global(Model model)
	{
		return "global/global";
	}

	@RequestMapping(value="/hero/detail", method=RequestMethod.GET)
	public String heroData(Model model)
	{
		model.addAttribute("heroDetails", globalHeroDataService.getGlobalHeroData());
		return "global/hero-details";
	}
	
}
