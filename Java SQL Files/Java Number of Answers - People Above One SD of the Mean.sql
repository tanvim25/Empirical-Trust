select owneruserid, count(id)
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%')
and owneruserid is not null
group by owneruserid
having count(id)>=77
order by count(id) desc

#Output
#2482 Users Returned