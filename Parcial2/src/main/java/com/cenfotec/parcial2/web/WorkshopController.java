package com.cenfotec.parcial2.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.parcial2.domain.Activity;
import com.cenfotec.parcial2.domain.Workshop;
import com.cenfotec.parcial2.service.ActivityService;
import com.cenfotec.parcial2.service.WorkshopService;



@Controller
public class WorkshopController {

	@Autowired
	WorkshopService workshopService;
	
	@Autowired
	ActivityService activityService;
	
	@RequestMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/insertar",  method = RequestMethod.GET)
	public String insertarPage(Model model) {
		model.addAttribute(new Workshop());
		return "insertar";
	}	
	
	@RequestMapping(value = "/insertar",  method = RequestMethod.POST)
	public String insertarAction(Workshop workshop, BindingResult result, Model model) {
		workshopService.save(workshop);
		return "index";
	}
	
	@RequestMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("workshops",workshopService.getAll());
		return "listar";
	}
	
	@RequestMapping(value = "/buscarNombre",  method = RequestMethod.GET)
	public String buscarPage(Model model) {
		model.addAttribute(new Workshop());
		return "buscarNombre";
	}	
	
	@RequestMapping("/listarNombre")
	public String listarNombre(Model model,@RequestParam String name) {
		model.addAttribute("workshopNombre",workshopService.find(name));
		return "listarNombre";
	}
	
	@RequestMapping("/listarCat")
	public String listarCat(Model model,@RequestParam String category) {
		model.addAttribute("workshopCat",workshopService.findCat(category));
		return "listarCat";
	}
	
	@RequestMapping("/edit/{id}")
	public String findWorkshopToEdit(Model model, @PathVariable long id) {
		Optional<Workshop> possibleData = workshopService.get(id);
		if (possibleData.isPresent()) {
			model.addAttribute("workshopToEdit",possibleData.get());
			return "edit";	
		}
		return "notfound";
	}

	@RequestMapping(value="/edit/{id}",  method = RequestMethod.POST)
	public String saveEdition(Workshop workshop, Model model, @PathVariable long id) {
		workshopService.save(workshop);
		return "index";
	}

	@RequestMapping(value="/detalle/{id}")
	public String saveEdition(Model model, @PathVariable long id) {
		Optional<Workshop> possibleData = workshopService.get(id);
		if (possibleData.isPresent()) {
			model.addAttribute("workshopData",possibleData.get());
			return "detalle";	
		}
		return "notfound";
	}

	@RequestMapping(value="/agregarActividad/{id}")
	public String recoverForAddActivity(Model model, @PathVariable long id) {
		Optional<Workshop> workshop = workshopService.get(id);
		Activity newActivity = new Activity();
		if (workshop.isPresent()) {
			newActivity.setWorkshop(workshop.get());
			model.addAttribute("workshop",workshop.get());
			model.addAttribute("activity",newActivity);
			return "agregarActividad";	
		}
		return "notfound";
	}
	
	@RequestMapping(value="/agregarActividad/{id}", method = RequestMethod.POST)
	public String saveActivity(Activity activity, Model model, @PathVariable long id) {
		Optional<Workshop> antology = workshopService.get(id);
		if (antology.isPresent()) {
			activity.setWorkshop(antology.get());
			activityService.save(activity);
			return "index";
		}
		return "errorArticle";
		/*
		Article newArticle = new Article();
		if (antology.isPresent()) {
			Antology updatedAntology = antology.get();
			updatedAntology.getArticles().add(article);
			anthologyService.save(updatedAntology);
			return "listar";	
		}*/
		/*return "notfound";*/
	}
	
}
