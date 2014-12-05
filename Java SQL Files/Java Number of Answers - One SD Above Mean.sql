select AVG(temp.NoOfAns) + STDEV(temp.NoOfAns) as OneSDAboveMean
from
(select owneruserid, count(id) as NoOfAns
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%')
and owneruserid is not null
group by owneruserid) as temp

#Output
#76.7424478305126 