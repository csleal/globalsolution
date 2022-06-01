package br.com.fiap.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import br.com.fiap.dao.VisitorDao;
import br.com.fiap.model.Visitor;
import br.com.fiap.service.UploadService;

@Named
@RequestScoped
public class VisitorBean {
	
	private Visitor visitor = new Visitor();
	
	private UploadedFile image;
	
	@Inject
	private VisitorDao dao;

	public String save() {
		System.out.println(visitor);
		
		String imagePath = UploadService.write(image, "visitors");
		
		visitor.setImagePath(imagePath);
		
		dao.create(visitor);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Visitante cadastrado com sucesso"));
		
		return "visitors";
	}
	
	public List<Visitor> getVisitors() {
		return dao.listAll();
	}
	
	public String delete(Visitor visitor) {
		dao.remove(visitor);
		
		showMessage("Visitante apagado com sucesso");
		return "visitors?faces-redirect=true";
	}

	private void showMessage(String msg) {
		FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getFlash()
			.setKeepMessages(true);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, new FacesMessage(msg));
	}
	
	public String edit() {
		dao.update(visitor);
		
		showMessage("Visitante atualizado com sucesso");
		return "visitors?faces-redirect=true";

		
	}
	
	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		this.image = image;
	}
}
