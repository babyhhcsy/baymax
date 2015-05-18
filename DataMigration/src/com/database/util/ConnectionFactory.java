package com.database.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.exception.DatabaseException;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class ConnectionFactory {
	public ConnectionFactory() {
		
	}

	private static ComboPooledDataSource comboPooledDataSource = null ;

	static {
		try {
			String url = PropertiesUtil.getUrl();
			String driver = PropertiesUtil.getDriver() ;
			String username = PropertiesUtil.getUsername();
			String password = PropertiesUtil.getPassword();
			Integer maxpoolsize = PropertiesUtil.getMaxpoolsize();
			Integer minpoolsize = PropertiesUtil.getMinpoolsize();
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(driver); 
			comboPooledDataSource.setJdbcUrl(url);
			comboPooledDataSource.setUser(username);
			comboPooledDataSource.setPassword(password);
			comboPooledDataSource.setMaxPoolSize(maxpoolsize);
			comboPooledDataSource.setMinPoolSize(minpoolsize);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws DatabaseException {
		Connection con = null;
		try {
			con = comboPooledDataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Throwable throwable = new Throwable("c3p0 connect is wrong!");
			throw new DatabaseException(" ", throwable);
		}
		return con;
	}
	
	public static void clearAll(Connection conn, Statement stmt, ResultSet res) throws SQLException {
		if (res != null) {
			res.close();
			res = null;
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if (conn != null) {
			conn.close();
			conn = null;
		}

	}
}
