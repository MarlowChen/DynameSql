package com.DynameSql.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DynameSql.Service.DynameSqlService;

/**
 * Servlet implementation class mainServlet
 */

public class DynamicJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DynameSqlService dynameSqlService;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DynamicJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		dynameSqlService = new DynameSqlService();
		
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("ColorType", request.getParameter("ColorType"));
		queryMap.put("MaterialType", request.getParameter("MaterialType"));
		queryMap.put("Price", request.getParameter("Price"));
		queryMap.put("IsSell", request.getParameter("IsSell"));
		
		List<Map<String,Object>> resultList =  dynameSqlService.getSearchResult(queryMap);
		
	     PrintWriter out = response.getWriter();
	     
	     for(Map<String,Object> aResult:resultList){
	      out.println("ClothesType =  "+ aResult.get("clothesType") );
	     }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
