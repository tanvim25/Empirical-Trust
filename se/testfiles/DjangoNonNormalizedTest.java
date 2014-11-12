package se.testfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DjangoNonNormalizedTest {
	
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
			List<Integer> scores = new ArrayList<Integer>();
			query = "select owneruserid, count(id) as score"
					+ " from XssAnswers"
					//+ " where parentId IN (select id from JavaPosts where posttypeid =1)"
					//+ " and owneruserid is not null"
					+ " group by OwnerUserId"
					+ " order by score desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			int sum = 0;
			double average = 0.0;
			while(rs.next())
			{
				int currAns = 0;
				System.out.print(rs.getInt(1)+"\t");
				System.out.println(rs.getInt(2));
				currAns = rs.getInt(2);
				sum = sum + currAns;
				scores.add(currAns);
				count++;				
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Sum Total Of All Posts : "+sum);
						
			average = (double)sum/(double)count;
			
			//Standard Deviation Logic
			double variance = 0.0;
			double stddev = 0.0;
			Iterator<Integer> scoreIterator = scores.iterator();
			System.out.println();
			while(scoreIterator.hasNext())
			{
				double currEle = (double)scoreIterator.next();
				double currEleCalc = Math.pow((currEle - average),2);
				//System.out.println(currEle+"  "+average+"  "+(currEle-average)+"  "+currEleCalc);
				variance = variance + currEleCalc;
				//System.out.println(variance);
			}
			variance = variance/(double)count;
			stddev = Math.sqrt(variance);
			System.out.println("Average Number of Posts : "+average);
			System.out.println("Standard Deviation : "+stddev);
			System.out.println();
			System.out.println("Mean : "+(int)Math.ceil((stddev*0)+average));
			int countAtMean = 0;
			scoreIterator = scores.iterator();
			while(scoreIterator.hasNext())
			{
				if(scoreIterator.next()>=(int)Math.ceil((stddev*0)+average))
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
				if(scoreIterator.next()>=(int)Math.ceil((stddev*1)+average))
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
				if(scoreIterator.next()>=(int)Math.ceil((stddev*2)+average))
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
				if(scoreIterator.next()>=(int)Math.ceil((stddev*3)+average))
				{
					countAtThreeMean++;
				}
			}
			System.out.println("Number of Users greater than or equal to three standard deviations above mean : "+countAtThreeMean);
			System.out.println("Percentage Remaining : "+((double)countAtThreeMean/(double)count)*100+"%");
			System.out.println();
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