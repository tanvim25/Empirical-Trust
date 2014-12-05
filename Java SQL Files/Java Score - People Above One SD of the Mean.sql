select owneruserid, sum(score) as TechScore
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%')
and owneruserid is not null
group by owneruserid
having sum(score)>=307
order by sum(score) desc

#Output
#Approximately 1306 Users Returned