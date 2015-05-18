package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.database.exception.DatabaseException;
import com.database.util.ConnectionFactory;


public class CommonDao {
	private Connection connection;

	public CommonDao() throws DatabaseException {
		this.connection = ConnectionFactory.getConnection();
	}


	public void begin() throws DatabaseException {
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
				System.out.println("----------------------------begin transaction success----------------------------");
			} catch (SQLException e) {
				System.out.println("----------------------------begin transaction failure----------------------------");
				throw new DatabaseException("can not begin transaction", e);
			}
		} else {
			throw new DatabaseException("connection not opened!");
		}
	}

	
	public void commit() throws DatabaseException {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.commit();
				connection.setAutoCommit(true);
				System.out.println("----------------------------------commit success--------------------------------");
			} else {
				if (connection == null) {
					throw new DatabaseException("connection not opened!");
				} else {
					throw new DatabaseException("first begin then commit please!");
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("can not commit transaction!", e);
		}
	}

	
	public void rollback() throws DatabaseException {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.rollback();
				connection.setAutoCommit(true);
				System.out.println("----------------------------------rollback success--------------------------------");
			} else {
				if (connection == null) {
					throw new DatabaseException("connection not opened!");
				} else {
					throw new DatabaseException("first begin then rollback please!");
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("can not rollback transaction!", e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List convert(ResultSet resultSet) throws DatabaseException {
	    List resultList = new ArrayList();
	    try {
	        ResultSetMetaData meta = resultSet.getMetaData();
	        int colCount = meta.getColumnCount();
	        while (resultSet.next()) {
	        	Map recordMap = new HashMap();
	            for (int i = 1; i <= colCount; i++) {
	            	String name = meta.getColumnName(i);
	                Object value = resultSet.getObject(i);
	                recordMap.put(name, value);
	            }
	            resultList.add(recordMap);
	        }
	    } catch (SQLException ex) {
	        throw new DatabaseException("can not convert result set to list of map", ex);
	    }
	    return resultList;
	}
	

	@SuppressWarnings("rawtypes")
	private void apply(PreparedStatement pstmt, List params) throws DatabaseException {
		try {
			if (params != null && params.size() > 0) {
				Iterator it = params.iterator();
	            int index = 1;
	            while(it.hasNext()) {
	            	Object obj = it.next();
	                if (obj == null) {
	                	pstmt.setObject(index, "");
	                } else {
	                    pstmt.setObject(index, obj);
	                }
	                index++;
	            }
	        }
	    } catch (SQLException ex) {
	    	throw new DatabaseException("can not apply parameter", ex);
	    }
	 }

	@SuppressWarnings("rawtypes")
	public List query(String sql, List params) throws DatabaseException {
        List result = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(sql);
            this.apply(pstmt, params);
            rs = pstmt.executeQuery();
            result = this.convert(rs);
        } catch (SQLException ex) {
            throw new DatabaseException("can not execute query", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // nothing
                	e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                	e.printStackTrace();
                }
            }
        }
        return result;
    }
	
	
	@SuppressWarnings("rawtypes")
	public Object queryOne(String sql, List params) throws DatabaseException {
        List list = this.query(sql, params);
        if(list == null || list.size() == 0) {
            throw new DatabaseException("data not exist");
        }else{
            Map record = (Map)list.get(0);
            if(record == null || record.size() == 0 ) {
                throw new DatabaseException("data not exist");
            } else {
                return record.values().toArray()[0];
            }
        }
    }
	

	@SuppressWarnings("rawtypes")
	public int execute(String sql, List params) throws DatabaseException {
		 int ret = 0;
		 PreparedStatement pstmt = null;
	     try {
	    	 pstmt = connection.prepareStatement(sql);
	         this.apply(pstmt, params);
	         ret = pstmt.executeUpdate();
	     }catch(SQLException ex) {
	         throw new DatabaseException("", ex);
	     } finally {
	         if (pstmt!= null) {
	        	 try {
	        		 pstmt.close();
	        		 pstmt = null;
	             } catch (SQLException e) {
	                 e.printStackTrace();
	             }
	         }
	     }
	     return ret;
	}
    
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List[] queryBatch(String[] sqlArray, List[] paramArray) throws DatabaseException {
	        List rets = new ArrayList();
	        if(sqlArray.length != paramArray.length) {
	            throw new DatabaseException("sql size not equal parameter size");
	        } else {
	            for(int i = 0; i < sqlArray.length; i++) {
	                String sql = sqlArray[i];
	                List param = paramArray[i];
	                List ret = this.query(sql, param);
	                rets.add(ret);
	            }
	            return (List[])rets.toArray();
	        }
	    }
	 
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public int[] executeBatch(String[] sqlArray, List[] paramArray) throws DatabaseException {
		 List rets = new ArrayList();
	     if(sqlArray.length != paramArray.length) {
	    	 throw new DatabaseException("sql size not equal parameter size");
	     }else{
	     	for(int i = 0; i < sqlArray.length; i++) {
	     		int ret = this.execute(sqlArray[i], paramArray[i]);
	            rets.add(new Integer(ret));
	        }
	        int[] retArray = new int[rets.size()];
	        for(int i = 0; i < retArray.length; i++) {
	        	retArray[i] = ((Integer)rets.get(i)).intValue();
	        }
	        return retArray;
	     }
	}
	

	public void close() throws DatabaseException{
		try {
	    	if (connection != null && connection.getAutoCommit()) {
	        	connection.close();
	        } else {
	        	if(connection == null) {
	            	throw new DatabaseException("can not close null connection, first new then close");
	            } else {
	            	throw new DatabaseException("transaction is running, rollbakc or commit befor close please.");
	            }
	        }
	    } catch (SQLException ex) {
	    	throw new DatabaseException("Can not close common dao");
	    }
	 }
}
