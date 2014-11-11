package se.analysis.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class XssGoldBadge {
	
	public static void main(String args[]) throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Got Class");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StackOverflow","root","");
			System.out.println("Got Connection");
			stmt = con.createStatement();
			System.out.println("Got Statement");
			String query;
			query = "select OwnerUserId, sum(score) as tech_sum, count(id) as ans_count"
					+ " from XssAnswers"
					//+ " where j1.id in (select id from JavaPosts where PostTypeId = 1 And tags like '%<java>%')"
					//+ " and j2.parentid = j1.Id"
					+ " group by OwnerUserId"
					+ " having sum(score) >= 1000"
					+ " and count(id) >= 200"
					+ " order by sum(score) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			//double fifteenabove = 0.0;
			//double onekabove = 0.0;
			//int countfifteen = 0;
			//int countonek = 0;
			//int onepc = 1260;
			while(rs.next())
			{
				int currCount = 0;
				System.out.print(rs.getInt(1)+"\t");
				currCount = rs.getInt(2);
				System.out.println(currCount);
				count++;
				/*if(count==onepc)
				{
					break;
				}*/
			}
			System.out.println("Number of Users :"+count);
			//System.out.println("Number of Users above 100 Accepted Answers : "+countfifteen);
			//System.out.println("Number of Users above 1k : "+countonek);
			//fifteenabove = (countfifteen/(double)count)*100;
			//onekabove = (countonek/count)*100;
			//System.out.println("Percentage above 100 accepted answers : "+fifteenabove+"%");
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