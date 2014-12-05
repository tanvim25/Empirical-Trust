select x2.OwnerUserId, sum(x2.score) as tech_sum, count(x2.id) as ans_count
from XSSQuestions x1, XSSAnswers x2
where x1.id in (select id from XSSQuestions)
and x2.parentid = x1.Id
group by x2.OwnerUserId
having sum(x2.score) >= 1000
and count(x2.id) >= 200
order by sum(x2.score) desc