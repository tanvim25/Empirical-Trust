select count(*)
from
(select p2.owneruserid, count(p2.id) as NoOfBadges
from Posts p1, Posts p2
where p2.parentid = p1.id
and p1.id in (select id from posts where posttypeid = 1 and tags like '%<java>%')
and p2.score >=25
and p2.owneruserid is not null
group by p2.OwnerUserId) as temp

#Output
#5158