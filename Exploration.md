# <center>Data Exploration Report</center> #
___

### <center>Empirical Track Topic - Expert Mining In Stack Overflow </center> ###
<center> Ankit Joshi (Unity ID - ajoshi5), Tanvi Mainkar (Unity ID - tmainka) </center>
___

**Step 1:** We started the project by exploring the Stack Overflow schema using [StackExchange Data Explorer](http://data.stackexchange.com/stackoverflow/query/new).

**Step 2:** We downloaded the September 2013 data dump of Stack Overflow from [this](http://meta.stackexchange.com/questions/198915/is-there-a-direct-download-link-with-a-raw-data-dump-of-stack-overflow-not-a-t) link.

**Step 3:** Currently we have imported data only from the **Posts** table since our initial investigation only involves entries from the **Posts** table. In order to import the data from the **Posts** table into our local MySQL database we followed two steps:-<br> 
1. We created the **Posts** table by referring to the schema given on the [StackExchange Data Explorer](http://data.stackexchange.com/stackoverflow/query/new) website.<br> 
2. We then split the **Posts.xml** file present in the Stack Overflow data dump into multiple XML's due to the large size of file and imported each XML individually in our local database with following command:-

`LOAD XML LOCAL INFILE 'D://se//ExpertMining//SplitFiles1//Posts001.xml' INTO TABLE posts ROWS IDENTIFIED BY '<row>';`

This command reads data from the *Posts001.xml* file and stores it into the *Posts* table. Every `<row>` element present in the *Posts001.xml* file is stored as a new tuple in the *Posts* table.

**Step 4:** In order to make the computation less expensive, we extracted data related to a particular technology (eg. *DjangoORM*) and stored it in a separate table. Below are the sample queries for the same:-

    create table DjangoORM 
	select * from posts 
	where tags like '%<django-orm>%' and posttypeid = 1;  
    
The above query creates a table (*DjangoORM*) and populates it with all the questions that are tagged as belonging to *DjangoORM*.

	insert into DjangoORM 
	select * from posts where parentid 
	IN (select id from posts where posttypeid = 1 and tags like '%<django-orm>%');

The above query populates the existing *DjangoORM* table with answers for the questions asked on *DjangoORM*.

**Step 5:** We calculated the net score of all the users for a particular technology (e.g. *DjangoORM*) i.e. the net number of votes the users have received for all the answers that they have given for questions which belong to that particular technology.

    select OwnerUserId, Sum(score)
    from DjangoORM
    where parentid in ( select id
    from DjangoORM
    where PostTypeId = 1 And tags like '%<django-orm>%')
    group by OwnerUserId
    having sum(score) >= 0
	order by Sum(score) desc

![Django ORM Score Query](https://github.ncsu.edu/CSC510-Fall2014/Empirical-Trust/raw/master/Score%20-%20Final.jpg "Django ORM Score Query")

The above query calculates the sum of the points that each user has received for answering questions that belong to *DjangoORM*.

**Step 6:** We then proceeded to calculate the average response time of all the users for a particular technology (eg. *DjangoORM*)

    select p2.owneruserid, avg(timestampdiff(SECOND, p1.creationdate, p2.creationdate)), 
	sum(timestampdiff(SECOND, p1.creationdate, p2.creationdate)), count(p2.id) 
	#TIMESTAMPDIFF Returns EXPR2-EXPR1
    from DjangoORM p1, DjangoORM p2
    where p2.parentId = p1.id and p1.id IN (select id from DjangoORM 
	where posttypeid = 1 and tags like '%<django-orm>%')
    group by p2.OwnerUserId

![Django ORM Response Time](https://github.ncsu.edu/CSC510-Fall2014/Empirical-Trust/raw/master/Response%20Time%20-%20Final.jpg, "Django ORM Response Time")

The above query calculates the average time that each user takes to respond to a question that belongs to *DjangoORM*.