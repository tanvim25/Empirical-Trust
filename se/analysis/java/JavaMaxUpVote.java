package se.analysis.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserAnswer;

public class JavaMaxUpVote {
	
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
			query = "select owneruserid, NumberOfMaxUpAns"
					+ " from JavaMaxUpVote"
					+ " order by NumberOfMaxUpAns desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			List<UserAnswer> scores = new ArrayList<UserAnswer>();
			while(rs.next())
			{
				int currTotal = 0;
				System.out.print(rs.getInt(1)+"\t");
				currTotal = rs.getInt(2);
				System.out.println(currTotal);
				count++;
				scores.add(new UserAnswer(rs.getInt(1), rs.getInt(2)));
			}
			System.out.println("Number of Users :"+count);
			
			query = "SELECT owneruserid from JavaTopScorers";
			rs = stmt.executeQuery(query);
			List<Integer>expectedExpert = new ArrayList<Integer>();
			while(rs.next())
			{
				expectedExpert.add(rs.getInt(1));
			}
			List<Integer>maxUpVotes = new ArrayList<Integer>();
			Iterator<UserAnswer> scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserAnswer currEle = scoreIterator.next();
				maxUpVotes.add(currEle.getUserId());
			}
			
			int overlap = 0;
			Iterator<Integer> expectedExpertIterator = expectedExpert.iterator();
			while(expectedExpertIterator.hasNext())
			{
				if(maxUpVotes.contains(expectedExpertIterator.next()))
				{
					overlap++;
				}
			}
			System.out.println("Overlaps : "+overlap);
		
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