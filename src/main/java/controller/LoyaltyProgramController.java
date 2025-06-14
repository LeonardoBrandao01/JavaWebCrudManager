package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.LoyaltyProgram;
import model.ModelException;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.LoyaltyProgramDAO;

@WebServlet(urlPatterns = {"/loyaltyprograms", "/loyaltyprogram/form", 
		"/loyaltyprogram/insert", "/loyaltyprogram/delete", "/loyaltyprogram/update"})
public class LoyaltyProgramController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/CrudManager/loyaltyprogram/form": {
			listCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-loyaltyprogram.jsp");			
			break;
		}
		case "/CrudManager/loyaltyprogram/update": {
			String idStr = req.getParameter("loyaltyProgramId");
			int idLoyaltyProgram = Integer.parseInt(idStr); 
			
			LoyaltyProgramDAO dao = DAOFactory.createDAO(LoyaltyProgramDAO.class);
			
			LoyaltyProgram loyaltyProgram = null;
			try {
				loyaltyProgram = dao.findById(idLoyaltyProgram);
			} catch (ModelException e) {
				e.printStackTrace();
			}
			
			listCompanies(req);
			req.setAttribute("action", "update");
			req.setAttribute("loyaltyProgram", loyaltyProgram);
			ControllerUtil.forward(req, resp, "/form-loyaltyprogram.jsp");
			break;
		}
		default:
			listLoyaltyPrograms(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/loyaltyprograms.jsp");
		}
	}
	
	private void listLoyaltyPrograms(HttpServletRequest req) {
		LoyaltyProgramDAO dao = DAOFactory.createDAO(LoyaltyProgramDAO.class);
		
		List<LoyaltyProgram> loyaltyPrograms = null;
		try {
			loyaltyPrograms = dao.findAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (loyaltyPrograms != null)
			req.setAttribute("loyaltyPrograms", loyaltyPrograms);
	}
	
	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/CrudManager/loyaltyprogram/insert": {
			insertLoyaltyProgram(req, resp);			
			break;
		}
		case "/CrudManager/loyaltyprogram/delete" :{
			
			deleteLoyaltyProgram(req, resp);
			
			break;
		}
		
		case "/CrudManager/loyaltyprogram/update" :{
			updateLoyaltyProgram(req, resp);
			break;
		}
		
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/loyaltyprograms");
	}

	private void updateLoyaltyProgram(HttpServletRequest req, HttpServletResponse resp) {
		String loyaltyProgramIdStr = req.getParameter("loyaltyProgramId");
		String nomePrograma = req.getParameter("nomePrograma");
		String milhasAcumuladasStr = req.getParameter("milhasAcumuladas");
		String status = req.getParameter("status");
		String dataValidadeStr = req.getParameter("dataValidade");
		Integer companhiaId = Integer.parseInt(req.getParameter("companhia"));
		
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram(Integer.parseInt(loyaltyProgramIdStr));
		loyaltyProgram.setNomePrograma(nomePrograma);
		loyaltyProgram.setMilhasAcumuladas(Integer.parseInt(milhasAcumuladasStr));
		loyaltyProgram.setStatus(status);
		
		try {
			Date dataValidade = new SimpleDateFormat("yyyy-MM-dd").parse(dataValidadeStr);
			loyaltyProgram.setDataValidade(dataValidade);
		} catch (ParseException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, "Erro ao formatar a data de validade.");
			return;
		}
		
		Company company = new Company(companhiaId);
		loyaltyProgram.setCompanhia(company);
		
		LoyaltyProgramDAO dao = DAOFactory.createDAO(LoyaltyProgramDAO.class);
		
		try {
			dao.update(loyaltyProgram);
			ControllerUtil.sucessMessage(req, "Programa de Fidelidade '" + 
						loyaltyProgram.getNomePrograma() + "' atualizado com sucesso.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}		
	}

	private void deleteLoyaltyProgram(HttpServletRequest req, HttpServletResponse resp) {
		String loyaltyProgramIdParameter = req.getParameter("id");
		
		int loyaltyProgramId = Integer.parseInt(loyaltyProgramIdParameter);
		
		LoyaltyProgramDAO dao = DAOFactory.createDAO(LoyaltyProgramDAO.class);
		
		try {
			LoyaltyProgram loyaltyProgram = dao.findById(loyaltyProgramId);
			
			if (loyaltyProgram == null)
				throw new ModelException("Programa de Fidelidade não encontrado para deleção.");
			
			dao.delete(loyaltyProgram);
			ControllerUtil.sucessMessage(req, "Programa de Fidelidade '" + 
						loyaltyProgram.getNomePrograma() + "' deletado com sucesso.");
		} catch (ModelException e) {
			if (e.getCause() instanceof 
					SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertLoyaltyProgram(HttpServletRequest req, HttpServletResponse resp) {
		String nomePrograma = req.getParameter("nomePrograma");
		String milhasAcumuladasStr = req.getParameter("milhasAcumuladas");
		String status = req.getParameter("status");
		String dataValidadeStr = req.getParameter("dataValidade");
		Integer companhiaId = Integer.parseInt(req.getParameter("companhia"));
		
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
		loyaltyProgram.setNomePrograma(nomePrograma);
		loyaltyProgram.setMilhasAcumuladas(Integer.parseInt(milhasAcumuladasStr));
		loyaltyProgram.setStatus(status);
		
		try {
			Date dataValidade = new SimpleDateFormat("yyyy-MM-dd").parse(dataValidadeStr);
			loyaltyProgram.setDataValidade(dataValidade);
		} catch (ParseException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, "Erro ao formatar a data de validade.");
			return;
		}
		
		Company company = new Company(companhiaId);
		loyaltyProgram.setCompanhia(company);
		
		LoyaltyProgramDAO dao = DAOFactory.createDAO(LoyaltyProgramDAO.class);
	
		try {
			dao.insert(loyaltyProgram);
			ControllerUtil.sucessMessage(req, "Programa de Fidelidade '" + loyaltyProgram.getNomePrograma() 
				+ "' salvo com sucesso.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
}


