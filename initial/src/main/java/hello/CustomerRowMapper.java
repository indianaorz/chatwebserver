package hello;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("id"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		return customer;
	}

}