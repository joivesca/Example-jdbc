package mx.com.mx.chilitech.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.mx.chilitech.repository.StudentRepository;

public class StudentController extends HttpServlet {
	private StudentRepository studentRepository;
	
	private static String STUDENT_SIGNUP = "contents/signup.jsp";
	private static String STUDENT_LOGIN  = "contents/login.jsp";
	private static String LOGIN_SUCCESS  = "contents/success.jsp";
	private static String LOGIN_FAILURE  = "contents/failure.jsp"; 
		
	
	public StudentController() {
		super();
		studentRepository = new StudentRepository(); 
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher(STUDENT_SIGNUP);
		view.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pageName = req.getParameter("pageName");
	      String forward = "";        
	      
	      if (studentRepository != null) {
	          if (pageName.equals("signup")) {
	              if (studentRepository.findByUserName(req
	                      .getParameter("userName"))) {
	                  req.setAttribute("message", "User Name exists. Try another user name");
	                  forward = STUDENT_SIGNUP;
	                  RequestDispatcher view = req
	                          .getRequestDispatcher(forward);
	                  view.forward(req, resp);
	                  return;
	              }

	              studentRepository.create(req.getParameter("userName"),
	            		  req.getParameter("password"),
	            		  req.getParameter("firstName"),
	            		  req.getParameter("lastName"),
	            		  req.getParameter("dateOfBirth"),
	            		  req.getParameter("emailAddress"));
	              forward = STUDENT_LOGIN;
	          } else if (pageName.equals("login")) {
	              boolean result = studentRepository.findByLogin(
	            		  req.getParameter("userName"),
	            		  req.getParameter("password"));
	              if (result == true) {
	                  forward = LOGIN_SUCCESS;
	              } else {
	                  forward = LOGIN_FAILURE;
	              }
	          }
	      }
	      RequestDispatcher view = req.getRequestDispatcher(forward);
	      view.forward(req, resp);
	}

}
