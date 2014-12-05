select  temp1.p_count/temp.u_count
from
(select count(distinct x2.owneruserid) as u_count
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id) as temp,
(select count(x2.id) as p_count
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id) as temp1;