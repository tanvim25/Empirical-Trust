package se.analysis.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserResponseObject;

public class XSSResponseTime {
	
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
			query = "select userid, avgrtime"
					+ " from XSSFilteredART"
					+ " where avgrtime <= 1440"
					+ " order by avgrtime";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			//int max = 76;
			double sumRespTime = 0.0;
			List<UserResponseObject> scores = new ArrayList<UserResponseObject>(); 
			while(rs.next())
			{
				//if(count==max)
				//	break;
				System.out.print(rs.getInt(1)+"\t");
				System.out.println(rs.getDouble(2));
				sumRespTime += rs.getDouble(2);
				scores.add(new UserResponseObject(rs.getInt(1), rs.getDouble(2)));
				count++;
			}
			System.out.println();
			System.out.println("Number of Users :"+count);
			//System.out.println("Sum of all Response Times : "+sumRespTime);
			double average = sumRespTime/(double)count;
			System.out.println("Average Response Time : "+average);
			
			//Standard Deviation Logic
			
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<UserResponseObject> scoreIterator = scores.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				double currAns = currEle.getResptime();
				double currEleCalc = Math.pow((currAns - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)count;
			stddev = Math.sqrt(variance);
			System.out.println("Average Response Time : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				if(currEle.getResptime()>=((stddev*0)+average))
				{
					countAtMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to mean : "+countAtMean);
			System.out.println("Percentage Remaining : "+((double)countAtMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("One Standard Deviation Above Mean : "+((stddev*1)+average));
			int countAtOMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				if(currEle.getResptime()>=((stddev*1)+average))
				{
					countAtOMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to one standard deviation above mean : "+countAtOMean);
			System.out.println("Percentage Remaining : "+((double)countAtOMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Two Standard Deviations Above Mean : "+((stddev*2)+average));
			int countAtTwoMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				if(currEle.getResptime()>=((stddev*2)+average))
				{
					countAtTwoMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to two standard deviations above mean : "+countAtTwoMean);
			System.out.println("Percentage Remaining : "+((double)countAtTwoMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Three Standard Deviations Above Mean : "+(int)Math.ceil((stddev*3)+average));
			int countAtThreeMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				if(currEle.getResptime()>=((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");
			System.out.println();
			
			//Percentage Overlapping
			query = "SELECT userId from XSSEstimatedExperts";
			rs = stmt.executeQuery(query);
			List<Integer>expectedExpert = new ArrayList<Integer>();
			while(rs.next())
			{
				expectedExpert.add(rs.getInt(1));
			}
			List<Integer>fastestUsers = new ArrayList<Integer>();
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserResponseObject currEle = scoreIterator.next();
				fastestUsers.add(currEle.getUserid());
			}
			
			int overlap = 0;
			Iterator<Integer> expectedExpertIterator = expectedExpert.iterator();
			while(expectedExpertIterator.hasNext())
			{
				if(fastestUsers.contains(expectedExpertIterator.next()))
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