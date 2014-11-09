package se.analysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaAcceptedAnswers {

	public static void main(String args[]) throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Got Class");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StackOverflow","root","tiger");
			System.out.println("Got Connection");
			stmt = con.createStatement();
			System.out.println("Got Statement");
			String query;
			query = "select OwnerUserId, count(id)"
					+ " from JavaPosts"
					+ " where id in "
					+ "(select acceptedanswerid from posts where PostTypeId = 1 And tags like '%<java>%')"
					+ " and OwnerUserId is not null"
					+ " group by OwnerUserId"
					+ " having count(id) >= 0"
					+ " order by count(id) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			double fifteenabove = 0.0;
			double onekabove = 0.0;
			int countfifteen = 0;
			int countonek = 0;
			int onepc = 1260;
			while(rs.next())
			{
				int temp = 0;
				System.out.print(rs.getInt(1)+"\t");
				temp = rs.getInt(2);
				System.out.println(temp);
				count++;
				/*if(count==onepc)
				{
					break;
				}*/
				if(temp>=100)
				{
					countfifteen++;
				}
				/*if(temp>=1000)
				{
					countonek++;
				}*/
				
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Number of Users above 100 Accepted Answers : "+countfifteen);
			//System.out.println("Number of Users above 1k : "+countonek);
			fifteenabove = (countfifteen/(double)count)*100;
			//onekabove = (countonek/count)*100;
			System.out.println("Percentage above 100 accepted answers : "+fifteenabove+"%");
			//System.out.println("Percentage above one thousand : "+onekabove+"%");
			rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
