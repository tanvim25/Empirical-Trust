package se.analysis.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.analysis.models.UserAnswer;

public class XssMaxUpVoteNoAccept {
	
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
			query = "select x2.OwnerUserId, count(x2.id)"
					+ " from XSSQuestions x1, XssAnswers x2"
					+ " where x2.parentid = x1.id"
					+ " and x2.score = (select max(x3.score) from XssAnswers x3 where x3.parentid=x1.id)"
					+ " and (x2.id <> x1.AcceptedAnswerId or x1.AcceptedAnswerId is null)"
					+ " group by x2.OwnerUserId"
					+ " order by count(x2.id) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			int sum = 0;
			double average = 0.0;
			List<UserAnswer> answers = new ArrayList<UserAnswer>();
			while(rs.next())
			{
				
				int currCount = 0;
				System.out.print(rs.getInt(1)+"\t");
				currCount = rs.getInt(2);
				if(currCount < 4)
					break;
				System.out.println(currCount);
				answers.add(new UserAnswer(rs.getInt(1), rs.getInt(2)));
				count++;
				sum+=currCount;
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Sum Total Of Max voted Answers that were not accepted: "+sum);
			
			average = (double)sum/(double)(count);
			
			//Standard Deviation Logic
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<UserAnswer> scoreIterator = answers.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				double currEle = (double)scoreIterator.next().getAccptedAnswers();
				double currEleCalc = Math.pow((currEle - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)(count-1);
			stddev = Math.sqrt(variance);
			System.out.println("Average Number of Posts : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+(int)Math.ceil((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = answers.iterator();
			while(scoreIterator.hasNext())
			{
				if(scoreIterator.next().getAccptedAnswers()>=(int)Math.ceil((stddev*0)+average))
				{
					countAtMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to mean : "+countAtMean);
			System.out.println("Percentage Remaining : "+((double)countAtMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("One Standard Deviation Above Mean : "+(int)Math.ceil((stddev*1)+average));
			int countAtOMean = 0;
			scoreIterator = answers.iterator();
			while(scoreIterator.hasNext())
			{
				if(scoreIterator.next().getAccptedAnswers()>=(int)Math.ceil((stddev*1)+average))
				{
					countAtOMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to one standard deviation above mean : "+countAtOMean);
			System.out.println("Percentage Remaining : "+((double)countAtOMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Two Standard Deviations Above Mean : "+(int)Math.ceil((stddev*2)+average));
			int countAtTwoMean = 0;
			scoreIterator = answers.iterator();
			while(scoreIterator.hasNext())
			{
				if(scoreIterator.next().getAccptedAnswers()>=(int)Math.ceil((stddev*2)+average))
				{
					countAtTwoMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to two standard deviations above mean : "+countAtTwoMean);
			System.out.println("Percentage Remaining : "+((double)countAtTwoMean/(double)count)*100+"%");
			System.out.println();
			System.out.println("Three Standard Deviations Above Mean : "+(int)Math.ceil((stddev*3)+average));
			int countAtThreeMean = 0;
			scoreIterator = answers.iterator();
			while(scoreIterator.hasNext())
			{
				if(scoreIterator.next().getAccptedAnswers()>=(int)Math.ceil((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");

			query = "SELECT userId from XSSEstimatedExperts";
			rs = stmt.executeQuery(query);
			List<Integer>expectedExpert = new ArrayList<Integer>();
			while(rs.next())
			{
				expectedExpert.add(rs.getInt(1));
			}
			List<Integer>mostAccepts = new ArrayList<Integer>();
			scoreIterator = answers.iterator();
			while(scoreIterator.hasNext())
			{
				UserAnswer currEle = scoreIterator.next();
				mostAccepts.add(currEle.getUserId());
			}
			
			int overlap = 0;
			Iterator<Integer> expectedExpertIterator = expectedExpert.iterator();
			while(expectedExpertIterator.hasNext())
			{
				if(mostAccepts.contains(expectedExpertIterator.next()))
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