package com.database.test;

import java.util.ArrayList;
import java.util.List;

import com.database.client.MigrationClient;
import com.database.exception.DatabaseException;

public class Test {
	public static void main(String[] args) throws DatabaseException {
		
		List a = new ArrayList();
		a.add(2);
		List list = MigrationClient.queryList("select * from test", null);
		System.out.println(list);
	}
}
