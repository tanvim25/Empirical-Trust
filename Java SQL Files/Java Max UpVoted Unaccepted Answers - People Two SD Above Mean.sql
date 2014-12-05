select p2.OwnerUserId, count(p2.id) as NoOfMaxUpVotedUnAcAnswers
from Posts p1, Posts p2
where p2.parentid = p1.id
and p1.id in (select id from posts where posttypeid = 1 and tags like '%<java>%')
and p2.score = (select max(p3.score) from posts p3 where p3.parentid=p1.id)
and (p2.id <> p1.AcceptedAnswerId or p1.AcceptedAnswerId is null)
and p2.owneruserid is not null
group by p2.OwnerUserId
having count(p2.id)>=66
order by count(p2.id) desc