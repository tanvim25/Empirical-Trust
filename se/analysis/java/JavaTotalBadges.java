package se.analysis.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserBadgesObject;

public class JavaTotalBadges {

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
			query = "select OwnerUserId, sum(NumberOfbadges) as TotalNoOfBadges"
					+ " from JavaTotalBadges"
					+ " group by OwnerUserId"
					+ " order by TotalNoOfBadges desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0; //Number of Users
			int sumScore = 0; //Sum total of all scores
			//int max = 203;
			List<UserBadgesObject> scores = new ArrayList<UserBadgesObject>();
			while(rs.next())
			{
				int currScore = 0;
				currScore = rs.getInt(2);
				if(currScore < 70)
					break;
				System.out.print(rs.getInt(1)+"\t");
				System.out.println(currScore);
				count++;
				sumScore += currScore;
				scores.add(new UserBadgesObject(rs.getInt(1), rs.getInt(2)));
				//if(count==max)
				//	break;
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Sum Total of All Users' Badges : "+sumScore);
			double average = (double)sumScore/(double)count;
			
			//Standard Deviation Logic
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<UserBadgesObject> scoreIterator = scores.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				UserBadgesObject currEle = scoreIterator.next();
				int currScore = currEle.getNoOfBadges();
				double currEleCalc = Math.pow((currScore - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)(count-1);
			stddev = Math.sqrt(variance);
			System.out.println("Average Number Of Badges : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+(int)Math.ceil((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserBadgesObject currEle = scoreIterator.next();
				if(currEle.getNoOfBadges()>=(int)Math.ceil((stddev*0)+average))
				{
					countAtMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to mean : "+countAtMean);
			System.out.println("Percentage Remaining : "+((double)countAtMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("One Standard Deviation Above Mean : "+(int)Math.ceil((stddev*1)+average));
			int countAtOMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserBadgesObject currEle = scoreIterator.next();
				if(currEle.getNoOfBadges()>=(int)Math.ceil((stddev*1)+average))
				{
					countAtOMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to one standard deviation above mean : "+countAtOMean);
			System.out.println("Percentage Remaining : "+((double)countAtOMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Two Standard Deviations Above Mean : "+(int)Math.ceil((stddev*2)+average));
			int countAtTwoMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserBadgesObject currEle = scoreIterator.next();
				if(currEle.getNoOfBadges()>=(int)Math.ceil((stddev*2)+average))
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
				UserBadgesObject currEle = scoreIterator.next();
				if(currEle.getNoOfBadges()>=(int)Math.ceil((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");
			System.out.println();
			
			//Percentage Overlapping
			query = "SELECT owneruserid from JavaTopScorers";
			rs = stmt.executeQuery(query);
			List<Integer>expectedExpert = new ArrayList<Integer>();
			while(rs.next())
			{
				expectedExpert.add(rs.getInt(1));
			}
			List<Integer>mostBadges = new ArrayList<Integer>();
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserBadgesObject currEle = scoreIterator.next();
				mostBadges.add(currEle.getUserId());
			}
			
			int overlap = 0;
			Iterator<Integer> expectedExpertIterator = expectedExpert.iterator();
			while(expectedExpertIterator.hasNext())
			{
				if(mostBadges.contains(expectedExpertIterator.next()))
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