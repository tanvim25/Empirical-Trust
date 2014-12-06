package se.analysis.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserTimeNumAnsObject;

public class JavaResponseTime {
	
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
			query = "select owneruserid, AvgRespTime, NumberOfAns"
					+ " from JavaResponseTime"
					+ " where AvgRespTime <= 1440"
					+ " order by AvgRespTime asc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			double resp = 0;
			List<UserTimeNumAnsObject> scores = new ArrayList<UserTimeNumAnsObject>();
			while(rs.next())
			{
				double currAvg = 0;
				System.out.print(rs.getInt(1)+"\t");
				currAvg = rs.getDouble(2);
				resp += currAvg;
				System.out.println(currAvg);
				count++;
				scores.add(new UserTimeNumAnsObject(rs.getInt(1), rs.getDouble(2), rs.getInt(3)));
			}
			System.out.println("Number of Users :"+count);
			double average = resp/(double)count;
			
			//Standard Deviation Logic
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<UserTimeNumAnsObject> scoreIterator = scores.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = (UserTimeNumAnsObject)scoreIterator.next();
				double currAns = currEle.getRespTime();
				double currEleCalc = Math.pow((currAns - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)(count-1);
			stddev = Math.sqrt(variance);
			System.out.println("Average Score : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+Math.ceil((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getRespTime()>=Math.ceil((stddev*0)+average))
				{
					countAtMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to mean : "+countAtMean);
			System.out.println("Percentage Remaining : "+((double)countAtMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("One Standard Deviation Above Mean : "+Math.ceil((stddev*1)+average));
			int countAtOMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getRespTime()>=Math.ceil((stddev*1)+average))
				{
					countAtOMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to one standard deviation above mean : "+countAtOMean);
			System.out.println("Percentage Remaining : "+((double)countAtOMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Two Standard Deviations Above Mean : "+Math.ceil((stddev*2)+average));
			int countAtTwoMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getRespTime()>=Math.ceil((stddev*2)+average))
				{
					countAtTwoMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to two standard deviations above mean : "+countAtTwoMean);
			System.out.println("Percentage Remaining : "+((double)countAtTwoMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Three Standard Deviations Above Mean : "+Math.ceil((stddev*3)+average));
			int countAtThreeMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getRespTime()>=Math.ceil((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");
			System.out.println();
			
			query = "SELECT owneruserid from JavaTopScorers";
			rs = stmt.executeQuery(query);
			List<Integer>expectedExpert = new ArrayList<Integer>();
			while(rs.next())
			{
				expectedExpert.add(rs.getInt(1));
			}
			List<Integer>fastestResponders = new ArrayList<Integer>();
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				fastestResponders.add(currEle.getUserid());
			}
			
			int overlap = 0;
			Iterator<Integer> expectedExpertIterator = expectedExpert.iterator();
			while(expectedExpertIterator.hasNext())
			{
				if(fastestResponders.contains(expectedExpertIterator.next()))
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