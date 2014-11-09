package se.analysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaGoldBadge {
	
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
			query = "select j2.OwnerUserId, Sum(j2.score) as tech_sum, count(j2.id) as ans_count"
					+ " from JavaPosts j1, JavaPosts j2"
					+ " where j1.id in ( select id from JavaPosts"
					+ " where PostTypeId = 1 And tags like '%<java>%')"
					+ " and j2.parentid = j1.Id"
					+ " group by j2.OwnerUserId"
					+ " having sum(j2.score) >= 1000"
					+ " and count(j2.id) >= 200"
					+ " order by Sum(j2.score) desc";
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
				int temp = 0;
				System.out.print(rs.getInt(1)+"\t");
				temp = rs.getInt(2);
				System.out.println(temp);
				count++;
				/*if(count==onepc)
				{
					break;
				}*/
				/*if(temp>=100)
				{
					countfifteen++;
				}
				/*if(temp>=1000)
				{
					countonek++;
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