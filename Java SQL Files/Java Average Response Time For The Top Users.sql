select p2.owneruserid, avg(DATEDIFF(MINUTE, p1.creationdate, p2.creationdate)) as AvgRespTime, count(p2.id) as NoOfAns
from posts p1, posts p2
where p2.parentId = p1.id
and p1.id in (select id from posts where posttypeid = 1 and tags like '%<java>%')
and p2.owneruserid is not null
group by p2.OwnerUserId
having count(p2.id)>=77
order by count(p2.id) desc

#Output
#2482 Results Returned