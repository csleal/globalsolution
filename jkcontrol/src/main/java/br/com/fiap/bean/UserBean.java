package br.com.fiap.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fiap.dao.UserDAO;
import br.com.fiap.model.User;

@Named
@RequestScoped
public class UserBean {
	
	private User user = new User();
	
	@Inject
	private UserDAO dao;
	
	public String save() {
		System.out.println(user);
		dao.create(user);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário cadastrado com sucesso"));
		
		return "users";
	}
	
	public List<User> getUsers() {
		return dao.listAll();
	}
	
	public String login() {
		
		if (dao.exist(user)) {
			FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.put("user", user);
			
			return "visitors";
		}
		
		mostrarMensagem();
		System.err.println("login invalido");
		return "login?faces-redirect=true";
			
	}
	
	public String logout() {
		FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getSessionMap()
			.remove("user");
		
		return "login?faces-redirect=true";
	}
	
	

	private void mostrarMensagem() {
		FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getFlash()
			.setKeepMessages(true);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Login inválido", ""));
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
