package in.indigenous.last.shelter.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.indigenous.last.shelter.models.APCInfo;
import in.indigenous.last.shelter.models.APCTroopsDetail;
import in.indigenous.last.shelter.services.AccountHeroDataService;

@Controller
@RequestMapping(value = "/account")
public class LastShelterAccountController {

	@Resource
	private AccountHeroDataService accountHeroDataService;

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
		model.addAttribute("heroDetails", accountHeroDataService.getAccountHeroData(account));
		return "account/hero-details";
	}

	@RequestMapping(value = "/apc/{account}", method = RequestMethod.GET)
	public String apc(Model model, @PathVariable int account) {
		model.addAttribute("account", account);
		List<APCInfo> info = accountHeroDataService.getAPCInfo(account);
		List<APCTroopsDetail> details = info.stream().map(i -> {
			return i.getDetails();
		}).flatMap(List::stream).collect(Collectors.toList());
		model.addAttribute("apcDetails", details);
		return "account/apc-details";
	}
}
