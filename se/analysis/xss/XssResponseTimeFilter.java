package se.analysis.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserTimeNumAnsObject;

public class XssResponseTimeFilter {
	
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
			query = "select x2.owneruserid, avg(timestampdiff(MINUTE, x1.creationdate, x2.creationdate)) as average_rtime, count(x2.id) as NumAns"
					+ " from XssQuestions x1, XssAnswers x2"
					+ " where x2.parentId = x1.id"
					+ " group by x2.OwnerUserId"
					+ " order by count(x2.id) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			int ansCount = 0;
			int max = 168;
			List<UserTimeNumAnsObject> scores = new ArrayList<UserTimeNumAnsObject>();
			while(rs.next())
			{
				System.out.print(rs.getInt(1)+"\t");
				System.out.print(rs.getDouble(2)+"\t\t\t");
				System.out.println(rs.getInt(3));
				count++;
				ansCount += rs.getInt(3);
				scores.add(new UserTimeNumAnsObject(rs.getInt(1), rs.getDouble(2), rs.getInt(3)));
				
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Sum of all Posts : "+ansCount);
			double average = (double)ansCount/(double)count;
			
			//Standard Deviation Logic
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<UserTimeNumAnsObject> scoreIterator = scores.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = (UserTimeNumAnsObject)scoreIterator.next();
				int currAns = currEle.getNumAns();
				double currEleCalc = Math.pow((currAns - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)count;
			stddev = Math.sqrt(variance);
			System.out.println("Average Score : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+(int)Math.ceil((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getNumAns()>=(int)Math.ceil((stddev*0)+average))
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
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getNumAns()>=(int)Math.ceil((stddev*1)+average))
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
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getNumAns()>=(int)Math.ceil((stddev*2)+average))
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
				UserTimeNumAnsObject currEle = scoreIterator.next();
				if(currEle.getNumAns()>=(int)Math.ceil((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");
			System.out.println();

			//Insert Experts Into Table
			scoreIterator = scores.iterator();
			int currUser = 0;
			double currRespTime = 0.0;
			int currScore = 0;
			int crec = 0;
			while(scoreIterator.hasNext())
			{
				UserTimeNumAnsObject currEle = scoreIterator.next();
				currUser = currEle.getUserid();
				currRespTime = currEle.getRespTime();
				currScore = currEle.getNumAns();
				if(currScore>=4) //Two SD's Above Mean
				{
					query = "INSERT INTO XSSFilteredART values ("+currUser+","+currRespTime+","+currScore+")";
					@SuppressWarnings("unused")
					int flag = stmt.executeUpdate(query);
					crec++;
				}
			}
			System.out.println();
			System.out.println("Insert Successfull");
			System.out.println("Values Inserted : "+crec);
			
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