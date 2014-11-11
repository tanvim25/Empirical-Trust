package se.analysis.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaTopScorers {
	
	public static void main(String args[]) throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
//			private static String driver = "com.mysql.jdbc.Driver";
//			private static String dbName="stackdb";
//			private static String connectionURL = "jdbc:mysql://localhost:3306/" + dbName;
//			private static Connection conn = null;

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Got Class");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","root","tiger");
			System.out.println("Got Connection");
			stmt = con.createStatement();
			System.out.println("Got Statement");
			String query;
			query = "select OwnerUserId, Sum(score)"
					+ " from JavaAnswers"
					//+ " where parentid in"
					//+ " (select id from posts where PostTypeId = 1 And tags like '%<java>%')"
					//+ " and OwnerUserId is not null"
					+ " group by OwnerUserId"
					+ " having sum(score) >= 0"
					+ " order by Sum(score) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0; //Number of Users
			double fivekabove = 0.0; //Percentage of Users having points above 5k
			double onekabove = 0.0; //Percentage of Users having points above 1k
			int countfivek = 0; //Users having points above 5k
			int countonek = 0; //Users having points above 1k
			//int onepc = 1260; //1% of the total number of users assumed initially to be experts
			while(rs.next())
			{
				int currScore = 0;
				System.out.print(rs.getInt(1)+"\t");
				currScore = rs.getInt(2);
				System.out.println(currScore);
				count++;
				/*if(count==onepc)
				{
					break;
				}*/
				if(currScore>=5000)
				{
					countfivek++;
				}
				if(currScore>=1000)
				{
					countonek++;
				}
				
			}
			System.out.println("Number of Users :"+count);
			System.out.println("Number of Users above 5k : "+countfivek);
			System.out.println("Number of Users above 1k : "+countonek);
			fivekabove = (countfivek/(double)count)*100;
			onekabove = (countonek/(double)count)*100;
			System.out.println("Percentage above five thousand : "+fivekabove+"%");
			System.out.println("Percentage above one thousand : "+onekabove+"%");
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