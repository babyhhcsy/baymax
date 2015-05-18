package com.database.client;


import java.util.List;

import com.database.dao.CommonDao;
import com.database.exception.DatabaseException;



public class MigrationClient {
	private static CommonDao commonDao = null ;
	
	/**********************************commondao*******************************************/
	static{
		try {
			commonDao = new CommonDao() ;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public static List queryList(String sql , List params) throws DatabaseException{
		List resultList = commonDao.query(sql, params);
		return resultList ;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Object queryOne(String sql , List params) throws DatabaseException{
		Object result = commonDao.queryOne(sql, params);
		return result ;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static boolean save(String sql , List params) throws DatabaseException{
		boolean flag = false ;
		int result = commonDao.execute(sql, params);
		if(result==0){
			flag = false ;
		}else{
			flag = true ;
		}
		return flag ;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static boolean modify(String sql , List params) throws DatabaseException{
		boolean flag = false ;
		int result = commonDao.execute(sql, params);
		if(result==0){
			flag = false ;
		}else{
			flag = true ;
		}
		return flag ;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static boolean delete(String sql , List params) throws DatabaseException{
		boolean flag = false ;
		int result = commonDao.execute(sql, params);
		if(result==0){
			flag = false ;
		}else{
			flag = true ;
		}
		return flag ;
	}
	
	
	
	
	
	public static void begin() throws DatabaseException{
		commonDao.begin();
	}
	
	public static void rollback() throws DatabaseException{
		commonDao.rollback();
	}
	
	public static void commit() throws DatabaseException{
		commonDao.commit();
	}

	
	/**************************************************************************************************************************************************/
	public String queryBatch(){
		return null ;
	}
	public boolean modifyBatch(){
		return false ;
	}
	
	
	
	

}
