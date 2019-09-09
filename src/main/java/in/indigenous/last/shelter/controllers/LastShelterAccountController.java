package in.indigenous.last.shelter.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.indigenous.last.shelter.services.account.AccountApcService;
import in.indigenous.last.shelter.services.account.AccountHeroService;

@Controller
@RequestMapping(value = "/account")
public class LastShelterAccountController {

	@Resource
	private AccountHeroService accountHeroService;

	@Resource
	private AccountApcService accountApcService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		return "account/home";
	}

	@RequestMapping(value = "/{account}", method = RequestMethod.GET)
	public String account(Model model, @PathVariable int account) {
		model.addAttribute("account", account);
		return "account/account";
	}

	@RequestMapping(value = "/hero/{account}", method = RequestMethod.GET)
	public String hero(Model model, @PathVariable int account) {
		model.addAttribute("account", account);
		model.addAttribute("heroDetails", accountHeroService.getAccountHeroDetails(account));
		return "account/hero-details";
	}

	@RequestMapping(value = "/apc/{account}", method = RequestMethod.GET)
	public String apc(Model model, @PathVariable int account) {
		model.addAttribute("account", account);
		model.addAttribute("apcDetails", accountApcService.getAccountAPCDetails(account));
		return "account/apc-details";
	}
}
