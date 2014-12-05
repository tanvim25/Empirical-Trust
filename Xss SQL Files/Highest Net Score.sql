select x2.OwnerUserId, count(x2.id)
	from XSSQuestions x1, XssAnswers x2
	where x2.parentid = x1.id
	and x2.score = (select max(x3.score) from XssAnswers x3 where x3.parentid=x1.id)
	group by x2.OwnerUserId
	order by count(x2.id) desc