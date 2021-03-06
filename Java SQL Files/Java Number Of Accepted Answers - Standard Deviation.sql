select STDEV(temp.NoOfAcAns)
from
(select OwnerUserId, count(id) as NoOfAcAns
from posts
where id IN (select AcceptedAnswerId from posts where posttypeid = 1 and tags like '%<java>%')
and owneruserid is not null
group by OwnerUserId) as temp

#Output
#45.8144764621854