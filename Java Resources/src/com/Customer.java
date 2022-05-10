package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/customer", "root", "12345");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertCustomer(int customerId, String firstName, String lastName, String nic, int phone, String email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into items (`customerId`,`firstName`,`lastName`,`nic`,`phone`,`email`)"
					+ " values (?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, nic);
			preparedStmt.setInt(5, phone);
			preparedStmt.setString(6, email);
			// execute the statement
			preparedStmt.execute();
			con.close();
//			String newCustomers = readItems();
//			output = "{\"status\":\"success\", \"data\": \"" + newCustomers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readCustomers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Item Code</th> <th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th> <th>Update</th><th>Remove</th></tr>";
			
			output = "<table border='1'><tr><th>Customer Id</th><th>First Name</th>" + "<th>Last Name</th>"
					+ "<th>Nic</th>" + "<th>Phone Number</th>" + "<th>Email</th>" + "<th>Address</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			
			String query = "select * from customer";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
			
				
				
				int customerId = rs.getInt("customerId");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String nic = rs.getString("nic");
				int phoneNumber = rs.getInt("phone");
				String email = rs.getString("email");
				String address = rs.getString("address");
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + customerId
						+ "'>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + phoneNumber + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + address+ "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
						+ customerId + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	



}
