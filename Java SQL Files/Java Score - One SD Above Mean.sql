select AVG(temp.TechScore) + STDEV(temp.TechScore) as OneSDAboveMean
from
(select owneruserid, sum(score) as TechScore
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%')
group by owneruserid
having sum(score)>=0) as temp

#Output
#306.703522251386