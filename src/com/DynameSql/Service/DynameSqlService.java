package com.DynameSql.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DynameSqlService {
	
    private static String querySql;
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT *");
        sb.append("FROM clothes C \n");
        sb.append("--REPLACE_JOIN_SQL \n");
        sb.append("--REPLACE_WHERE_SQL \n");
 
        querySql = sb.toString();
    }
    
    private static Map<String, String> queryWhereStringMap = new HashMap<String, String>();
 	static {
 		queryWhereStringMap.put("ColorType", "A.colorType like :ColorType ");
 		queryWhereStringMap.put("MaterialType", "A.materialType like :MaterialType ");
 		queryWhereStringMap.put("Price", "T.price <= :Price ");
 		queryWhereStringMap.put("IsSell", "T.isSell = :IsSell "); 		
 	}
    
    public String genJoinSql(final String sql, Map<String, Object> queryMap) {
    	String _sql = new String(sql);
    	LinkedList<String> allJoinSqlList = new LinkedList<String>();

    	for(String key : queryMap.keySet()){
		    Object value = queryMap.get(key);
		    if(!getJoinResultMap(queryMap,key).equals("") && (value != null) && (!"".equals(value))){
		    	
		    	LinkedList<String> joinResult = getJoinResultMap(queryMap,key);
		    	for(int i=joinResult.size()-1; i>=0;i--){
		    		allJoinSqlList.addFirst(joinResult.get(i));
		    	}

		    }
	    }
	     
    	if(allJoinSqlList.size()!=0){
    		return  _sql.replaceAll("--REPLACE_JOIN_SQL", isNotRepeat(allJoinSqlList));
    	}else{
    		return  _sql.replaceAll("--REPLACE_JOIN_SQL", "--REPLACE_JOIN_SQL".replace("\n", ""));
    	}
    }

    public String genWhereCountSql(final String sql, Map<String, Object> queryMap) {
    	String _sql = new String(sql);
    	StringBuilder whereSql = new StringBuilder();
    	
    	boolean isWhereFirst = true;

	    for(String key : queryMap.keySet()){
		    Object value = queryMap.get(key);
		    
		    if(queryWhereStringMap.get(key) != null && (value != null) && (!"".equals(value))){
		    	whereSql.append(isWhereFirst ? "WHERE " : "AND ").append(queryWhereStringMap.get(key) + "\n");
		    	isWhereFirst = false;
		    }
	    }
	    
    	return  _sql.replace("--REPLACE_WHERE_SQL", whereSql.toString());
    }
    
 	
    
    private LinkedList<String> getJoinResultMap(Map<String, Object> queryMap , String queryObject){
    	
    	LinkedList<String>builderArray =  new LinkedList<String>();
    	
	    	Object value = queryMap.get(queryObject);
	    	switch (queryObject) {
				case "ColorType":
					
					if(value!=null){
						builderArray.add("LEFT OUTER JOIN attribute A ON (A.id = C.attributesId) \n");						
					}					
					break;
				
				case "MaterialType"	: 
					if(value!=null){
						builderArray.add("LEFT OUTER JOIN attribute A ON (A.id = C.attributesId) \n"); 
					}
					break;
					
				case "Price" :
					if(value!=null){
						builderArray.add("LEFT OUTER JOIN tradingStatus T ON (T.id = C.tradingStatusId) \n"); 
					}
					break;
					
				case "IsSell" :
					if(value!=null){
						builderArray.add("LEFT OUTER JOIN tradingStatus T ON (T.id = C.tradingStatusId) \n"); 
					}
					break;	
					
				default:
					break;
			}
    	return builderArray;
    }
    
    private String isNotRepeat(LinkedList<String> allJoinSqlList){
    	
    	LinkedList<String> newbuilder = new LinkedList<String>();
    
    	do {
    		String first = allJoinSqlList.getFirst();
    			allJoinSqlList.removeFirst();	
					for(int i=0; i<allJoinSqlList.size();i++){
							if(first.equals(allJoinSqlList.get(i))){
								allJoinSqlList.remove(i);
							}
					}
				newbuilder.add(first);
				
		} while (!allJoinSqlList.isEmpty());
 
    	
		 StringBuilder sb = new StringBuilder();
		 
			for(int j=0; j<newbuilder.size();j++){
				
				if(j==(newbuilder.size()-1)){
					String lastsb = newbuilder.get(j).replace("\n", "");
					sb.append(lastsb);
				}else{
					sb.append(newbuilder.get(j));
				}
			}
    	
    	return sb.toString();
    }
    
    private String setSqlWhere(String Sql,String aMap,Object aValue){
    	String newValue = "" ;
    	if(aMap.equals("MaterialType") || aMap.equals("ColorType")){
    		newValue = "'%"+ aValue + "%'";
    	}else if(aMap.equals("Price"))
    	{
    		newValue = aValue.toString() ;	
    	}
    	else
    		newValue = "'"+ aValue + "'";
    	String newSql = Sql.replace(":"+aMap , newValue );
    	return newSql;
    	
    }
    
    
	public List<Map<String,Object>> getSearchResult(Map<String,Object> searchMap){
		String conUrl = "jdbc:mysql://localhost:3306/clothesdb?user=root&password="; //for jdbc 2.0
		Connection con = null;
		
		List<Map<String,Object>> resultList = new LinkedList<>();
		try {
		//JODBC
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conUrl);
		//SQL
		String SQL = genJoinSql(querySql, searchMap);
		SQL = genWhereCountSql(SQL, searchMap);
				
		Statement stmt = con.createStatement();
		
		for(String aMap:searchMap.keySet()){
			if(searchMap.get(aMap)!=null && !searchMap.get(aMap).equals("")){
			SQL = setSqlWhere(SQL, aMap ,searchMap.get(aMap));
			}
		}
	
		PreparedStatement pstmt = con.prepareStatement(SQL);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("id", rs.getString("id"));
			resultMap.put("clothesType", rs.getString("clothesType"));
			resultList.add(resultMap);
		}
			rs.close();
			stmt.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}		
		return resultList;
		
	}
	
}
