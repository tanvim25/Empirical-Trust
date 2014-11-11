package se.analysis.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class XssEnlightenedBadge {
	
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
			query = "select x2.OwnerUserId, count(x2.id)"
					+ " from XSSQuestions x1, XssAnswers x2"
					//+ " where x1.id in (select id from JavaPosts where posttypeid = 1 and tags like '%<java>%'"
					//+ " where x2.parentId = x1.id"
					+ " where x2.score >= 10"
					+ " and x2.id = x1.acceptedanswerid"
					+ " and x2.creationdate = (select min(x5.CreationDate) from XssAnswers x5 where x5.ParentId = x1.id)"
					+ " and x2.owneruserid <> x1.owneruserid"
					//+ " or ((select x4.OwnerUserId from XssAnswers x4 where x4.id = x1.id) is null and x2.owneruserid is not null))"
					+ " group by x2.OwnerUserId"
					+ " order by count(x2.id) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			double fifteenabove = 0.0;
			//double onekabove = 0.0;
			int countfifteen = 0;
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
				if(currCount>=10)
				{
					countfifteen++;
				}
				/*if(temp>=1000)
				{
					countonek++;
				}*/
				
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Number of Users above 10 Enlightened Badges : "+countfifteen);
			//System.out.println("Number of Users above 1k : "+countonek);
			fifteenabove = (countfifteen/(double)count)*100;
			//onekabove = (countonek/count)*100;
			System.out.println("Percentage above 10 Enlightened Badges : "+fifteenabove+"%");
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