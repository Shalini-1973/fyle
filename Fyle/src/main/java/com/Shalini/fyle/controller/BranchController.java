package com.Shalini.fyle.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Shalini.fyle.dto.BranchDto;
import com.Shalini.fyle.entities.Banks;
import com.Shalini.fyle.entities.Branch;
import com.Shalini.fyle.entities.Favorite;
import com.Shalini.fyle.repo.BankRepository;
import com.Shalini.fyle.repo.FavoriteRepository;
import com.Shalini.fyle.services.BranchServiceImpl;

@Controller
public class BranchController {

	@Autowired
	private BranchServiceImpl bs;

	@Autowired
	private BankRepository br;

	@Autowired
	private FavoriteRepository fabRepo;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<BranchDto> listbranch = new ArrayList<BranchDto>();
		model.addAttribute("branch", new BranchDto());
		model.addAttribute("listbranch", listbranch);
		model.addAttribute("bank",new Banks());
		return "branches";
	}

	/*
	 * @GetMapping("/find") public String find(Model model) {
	 * 
	 * return "find"; }
	 */
	@GetMapping("/mybranches")
	public ModelAndView findlist(@ModelAttribute("city") String data) {
		ModelAndView mav = new ModelAndView("branches");
		if (data == null) {
			List<BranchDto> listbranch = new ArrayList<BranchDto>();
			mav.addObject("listbranch", listbranch);
			mav.addObject("branch", new BranchDto());

		} else {
			List<Branch> branches = bs.getResult(data);
			Set<Branch> branchSet = bs.listToSet(branches);
			List<BranchDto> branchDto = bs.convertToDto(branchSet);
			Set<BranchDto> listBranch = new HashSet<>();
			if (branchDto.size() > 3) {
				for (int i = 0; listBranch.size() < 3 && i < branchDto.size(); i++) {
					if (!listBranch.contains(branchDto.get(i))) {
						listBranch.add(branchDto.get(i));
					}
				}
				mav.addObject("listbranch", listBranch);
		
			} else {
				System.out.println(branchDto.toString());
				mav.addObject("listbranch", branchDto);
			}
			mav.addObject("branch", new BranchDto());
		}
		return mav;
	}

	@GetMapping("/branches/autocomplete")
	public ResponseEntity<?> autocomplete(@RequestParam Map<String, String> input) {
		String q = "";
		int limit = 0;
		int offset = 0;
		if (input.containsKey("q")) {
			q = input.get("q");
		}
		if (input.containsKey("limit")) {
			limit = Integer.parseInt(input.get("limit"));
		}
		if (input.containsKey("offset")) {
			offset = Integer.parseInt(input.get("offset"));
		}
		
		if(limit<1) {
			return ResponseEntity.ok("LIMIT can't be less than 1");
		}
		if(offset<0) {
			return ResponseEntity.ok("OFFSET can't be less than 0");
		}

 
		List<Branch> list = bs.getBranches(q,limit,offset);
		System.out.println(list);
		Set<Branch> set = new HashSet<>(list);
		List<BranchDto> branchDto = bs.convertToDto(set);
		// Syste
		return ResponseEntity.ok(branchDto);
	}
	
	@GetMapping("/branches")
	public ResponseEntity<?> findByCity(@RequestParam Map<String, String> input) {
		String q = "";
		int limit = 0;
		int offset = 0;
		if (input.containsKey("q")) {
			q = input.get("q");
		}
		if (input.containsKey("limit")) {
			limit = Integer.parseInt(input.get("limit"));
		}
		if (input.containsKey("offset")) {
			offset = Integer.parseInt(input.get("offset"));
		}
		
		if(limit<1) {
			return ResponseEntity.ok("LIMIT can't be less than 1");
		}
		if(offset<0) {
			return ResponseEntity.ok("OFFSET can't be less than 0");
		}

 
		List<Branch> list = bs.getBranchesByCity(q,limit,offset);
		System.out.println(list);
		Set<Branch> set = new HashSet<>(list);
		List<BranchDto> branchDto = bs.convertToDto(set);
		// Syste
		return ResponseEntity.ok(branchDto);
	}
	
	@GetMapping("/bank/{name}")
	public ModelAndView getBankName(@PathVariable(name = "name") String name,Model model) {
		System.out.println(name);
		Banks bank = br.findByName(name);
		ModelAndView mav = new ModelAndView("bank");
		mav.addObject("bank",bank);
		mav.addObject("favorite","Not Marked as Favourite");
		return mav;
	}
	
	@GetMapping("/favorite/{name}")
	public ModelAndView favoriteBankName(@PathVariable(name = "name") String name,Model model) {
		System.out.println(name);
		Banks bank = br.findByName(name);
		if(!fabRepo.existsByBank(name)) {
		Favorite fab = new Favorite();
		fab.setBank(name);
		fabRepo.save(fab);
		}
		ModelAndView mav = new ModelAndView("bank");
		
		mav.addObject("bank",bank);
		mav.addObject("favorite","marked as favourite");
		return mav;
	}
	
	@GetMapping("/favor")
	public ModelAndView favoritelist() {
		
		ModelAndView mav = new ModelAndView("Favorite");
		List<Favorite> listfavorite = fabRepo.findAll();
		System.out.println(listfavorite);
		mav.addObject("listfavorite",listfavorite);
		return mav;
	}


}
