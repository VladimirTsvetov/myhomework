-- количество проданных билетов и выручка по фильмам
select film.name as film_name, count(tickets.id) as tickets, sum(tickets.cost) as total_cost from film 
join screening on film_id = film.id
join tickets on id_sc = screening.id
group by film.name
order by total_cost;

-- среднее количество зрителей на сеанс по фильмам
select avg(tickets.id_sc) as avarange_quantity, film.name as film from tickets
join screening on id_sc = screening.id
join film on film_id = film.id
group by film.name;

-- продолжительность фильмов
select distinct film.name as film_name, time(film.duration) as duration from film 
join screening on film_id = film.id
order by film_name;


-- накладки в сеансах
select film.name as film_name, screening.final, screening.time, film.duration from screening
join film on film_id = film.id
group by film.name
having (time_to_sec(screening.final) - time_to_sec(screening.time) - time_to_sec(film.duration)) <= 0


