select count(*)
from
(select p2.OwnerUserId, count(p2.id) as NoOfBadges
from Posts p1, Posts p2 
where p2.parentid = p1.id
and p1.id in (select id from posts where posttypeid = 1 and tags like '%<java>%')
and p2.score >= 10
and p2.Id = p1.acceptedanswerid
and p2.CreationDate = (select min(p5.CreationDate) from Posts p5 where p5.ParentId = p1.id)
and p2.owneruserid <> p1.owneruserid
group by p2.OwnerUserId) as temp

#Output
#4526