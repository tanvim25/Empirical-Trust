package se.analysis;

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
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Got Class");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StackOverflow","root","tiger");
			System.out.println("Got Connection");
			stmt = con.createStatement();
			System.out.println("Got Statement");
			String query;
			query = "select OwnerUserId, Sum(score)"
					+ " from JavaPosts"
					+ " where parentid in "
					+ "(select id from posts where PostTypeId = 1 And tags like '%<java>%')"
					+ " and OwnerUserId is not null"
					+ " group by OwnerUserId"
					+ " having sum(score) >= 0"
					+ " order by Sum(score) desc";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Got ResultSet");
			int count = 0;
			double fivekabove = 0.0;
			double onekabove = 0.0;
			int countfivek = 0;
			int countonek = 0;
			int onepc = 1260;
			while(rs.next())
			{
				int temp = 0;
				System.out.print(rs.getInt(1)+"\t");
				temp = rs.getInt(2);
				System.out.println(temp);
				count++;
				/*if(count==onepc)
				{
					break;
				}*/
				if(temp>=5000)
				{
					countfivek++;
				}
				if(temp>=1000)
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
