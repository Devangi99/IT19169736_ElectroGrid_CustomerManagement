package com;
import java.sql.*;

public class Customer {
	
	public Connection connect()
	{
			Connection con = null;
			try
			{
			
				Class.forName("com.mysql.jdbc.Driver");
				con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
			
				//For testing
				System.out.print("Successfully connected");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return con;
	}
	
public String insertCustomer(String customerName, String customerNic, String customerEmail, String customerPhone, String customerAddress)
		{
			String output = "";
			try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into customer (`customerId`,`customerName`,`customerNic`,`customerEmail`,`customerPhone`, `customerAddress`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, customerNic);
			preparedStmt.setString(4, customerEmail);
			preparedStmt.setString(5, customerPhone);
			preparedStmt.setString(6, customerAddress);


			// execute the statement
			preparedStmt.execute();
			con.close();
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" +newCustomer + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the customer.\"}";
			System.err.println(e.getMessage());
		}
			
		return output;
	}

public String readCustomer()
{
		String output = "";
		try
		{
				Connection con = connect();
					if (con == null)
					{
						return "Error while connecting to the database for reading.";
					}

					//Prepare the HTML table to be displayed
					output = "<table border='3'>" 
							+"<tr><th>Name</th>" + 
							"<th>NIC</th>" + 
							"<th>Email</th>" + 
							"<th>Phone</th>" + 
							"<th>Address</th>" + 
							"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from customer";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);

					// iterate through the rows in the result set
					while (rs.next())
					{
						 String customerId = Integer.toString(rs.getInt("customerId")); 
						 String customerName = rs.getString("customerName"); 
						 String customerNic = rs.getString("customerNic"); 
						 String customerEmail = rs.getString("customerEmail"); 
						 String customerPhone = rs.getString("customerPhone"); 
						 String customerAddress = rs.getString("customerAddress"); 



						// Add a row into the html table
						output += "<tr><td><input id='hidCustomerIDUpdate'name='hidCustomerIDUpdate'type='hidden' value='" + customerId  + "'>"+ customerName  + "</td>";
						output += "<td>" + customerNic + "</td>";
						output += "<td>" + customerEmail + "</td>";
						output += "<td>" + customerPhone + "</td>";
						output += "<td>" + customerAddress + "</td>";


						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' " ///////
								+ "class='btnUpdate btn btn-secondary' data-customerid='" + customerId + "'></td>" ////
								+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customerid='" + customerId + "'></td></tr>"; 
					}
					con.close();


					// Complete the html table
					output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the customer.";
				System.err.println(e.getMessage());
			}
			return output;
		}
	public String deleteCustomer(String customerId) 
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}


			// create a prepared statement
			String query = "delete from customer where customerId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(customerId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			//output = "Deleted successfully";
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" +newCustomer + "\"}";
			}
		catch (Exception e)
		{
			//output = "Error while deleting the customer.";
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the customer.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	//method to update bill details in DB
	public String updateCustomer(String customerId, String customerName, String customerNic, String customerEmail, String customerPhone, String customerAddress)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
				
			// create a prepared statement
			String query = "UPDATE customer SET customerName=?,customerNic=?,customerEmail=?,customerPhone=?, customerAddress=? WHERE customerId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, customerName); 
			 preparedStmt.setString(2, customerNic); 
			 preparedStmt.setString(3, customerEmail); 
			 preparedStmt.setString(4, customerPhone); 
			 preparedStmt.setString(5, customerAddress); 
			preparedStmt.setInt(6, Integer.parseInt(customerId));

			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Updated Customer details successfully";
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" +newCustomer + "\"}"; }
		catch (Exception e)
		{
			//output = "Error while updating the Customer Details.";
			output = "{\"status\":\"error\", \"data\":\"Error while updating the customer.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}


}

