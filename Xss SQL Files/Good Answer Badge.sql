select x2.owneruserid, count(x2.id)
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=25
group by x2.OwnerUserId
order by count(x2.id) desc