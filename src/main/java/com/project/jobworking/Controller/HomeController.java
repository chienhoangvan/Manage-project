package com.project.jobworking.Controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String redirectToHome() {

		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<? extends GrantedAuthority> role = new ArrayList<>();
		role = principal.getAuthorities();

		if (role.toString().equals("[admin]")) {
			return "redirect:/admin";
		} else if (role.toString().equals("[teacher]")) {
			return "redirect:/teacher";
		} else return "redirect:/student";
	}
}
