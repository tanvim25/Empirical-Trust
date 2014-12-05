select OwnerUserId, sum(score) #Score Per User Per Technology
from XSSAnswers
where parentid in (select id from XSSQuestions)
group by OwnerUserId
having sum(score) >= 0
order by sum(score) desc
LIMIT 5000