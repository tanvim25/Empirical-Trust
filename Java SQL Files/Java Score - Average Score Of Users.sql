select AVG(temp.TechScore)
from
(select owneruserid, sum(score) as TechScore
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%')
group by owneruserid
having sum(score)>=0) as temp

#Output
#16