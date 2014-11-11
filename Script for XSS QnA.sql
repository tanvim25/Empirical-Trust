select * from posts
LIMIT 1000;

#id - required
#posttypeid - not needed
#acceptedanswerid = needed
#parentid = needed
#creation date = needed
#score = needed
#owneruserid = needed
#tags - needed
#answercount = needed

create table XSSQuestions
(
select id,acceptedanswerid,creationdate,score,owneruserid,answercount
from posts
where posttypeid = 1
and tags like '%<xss>%'
and owneruserid is not null
);

SET SQL_SAFE_UPDATES = 0;

delete from JavaQuestions
where owneruserid is null;

select count(*)
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<java>%');

commit;

create table XSSAnswers
(
select id,parentid,creationdate,score,owneruserid
from posts
where parentid IN (select id from posts where posttypeid = 1 and tags like '%<xss>%')
and owneruserid is not null
);