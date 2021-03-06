package com.bron.demoJPA.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.bron.demoJPA.appuser.AppUser;
import com.bron.demoJPA.appuser.OpeningHour;
import com.bron.demoJPA.repository.OpeningHourRepository;
import com.bron.demoJPA.service.OpeningHourService;

@Controller
public class OpeningHourController {

	@Autowired
	private OpeningHourService openingHourService;

	@Autowired
	private OpeningHourRepository openingHourRepository;

	@GetMapping("/user/openinghour/add")
	public String showOpeningHourForm(Model model) {
		OpeningHour openinghour = new OpeningHour();
		model.addAttribute("openinghour", openinghour);
		return "user_openinghours_save";
	}

	@PostMapping("/user/openinghour/save")
	public String saveOpeningHour(@ModelAttribute("openinghour") OpeningHour openinghour) {
		openingHourService.saveOpeningHourWithUserId(openinghour);
		return "redirect:/user/openinghours/view";
	}

	@GetMapping("/showOpeningForUpdate/{openingHourID}")
	public String showOpeningForUpdate(@PathVariable(value = "openingHourID") long openingHourID, Model model) {
		OpeningHour openingHour = openingHourService.getOpeningHourByOpeningHourID(openingHourID);
		model.addAttribute("openinghour", openingHour);
		return "user_openinghours_update";

	}

	@GetMapping("/user/openinghours/view")
	public String viewOpeningPage(Model model) {
		AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = user.getId();
		model.addAttribute("listopeninghours", openingHourRepository.getOpeningHourByRestaurantID(id));
		return "user_openinghour_index";

	}

}
