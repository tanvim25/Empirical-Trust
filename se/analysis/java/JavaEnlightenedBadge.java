package se.analysis.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaEnlightenedBadge {
	
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
			query = "select j2.OwnerUserId, count(j2.id)"
					+ " from JavaQuestions j1, JavaAnswers j2"
					//+ " where j1.id in (select id from JavaPosts where posttypeid = 1 and tags like '%<java>%'"
					//+ " and j2.parentId = j1.id"
					+ " where j2.score >= 10"
					+ " and j2.id = j1.acceptedanswerid"
					+ " and j2.creationdate = (select min(j5.CreationDate) from JavaAnswers j5 where j5.ParentId = j1.id)"
					+ " and (j2.OwnerUserId <> j1.OwnerUserId)"
					//+ " or ((select j4.OwnerUserId from JavaPosts j4 where j4.id = j1.id) is null and j2.owneruserid is not null))"
					+ " group by j2.OwnerUserId"
					+ " order by count(j2.id) desc";
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