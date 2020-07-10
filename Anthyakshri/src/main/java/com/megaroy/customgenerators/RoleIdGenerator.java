package com.megaroy.customgenerators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class RoleIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		String PK=null;
		try {
			connection = session.connection();
			statement = connection.createStatement();
			String count_query="select count(role_id) from roles_table";
			rs=statement.executeQuery(count_query);
			int res=0;
			if(rs.next()) {
				res= rs.getInt(1)+1;
			}
			int count=1000+res;
			LocalDate date=LocalDate.now();
			if(date.getMonthValue()<10){
			PK="MA"+date.getYear()+"0"+date.getMonthValue()+count;
			}else {
		    PK="MA"+date.getYear()+date.getMonthValue()+count;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return PK;
	}

}
