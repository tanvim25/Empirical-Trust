select AVG(temp.NoOfMaxUpVotedAnswers) + (STDEV(temp.NoOfMaxUpVotedAnswers)*2)
from
(select p2.OwnerUserId, count(p2.id) as NoOfMaxUpVotedAnswers
from Posts p1, Posts p2
where p2.parentid = p1.id
and p1.id in (select id from posts where posttypeid = 1 and tags like '%<java>%')
and p2.score = (select max(p3.score) from posts p3 where p3.parentid=p1.id)
and p2.owneruserid is not null
group by p2.OwnerUserId) as temp

#Output
#123.470634149104