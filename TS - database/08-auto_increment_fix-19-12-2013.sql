alter table point_list drop index number_UNIQUE; -- punkt moze byc wiele razy w jednej trasie (ktos moze chciec sobie zrobic kolko)
alter table favourite_route modify idfavourite_route int(11) auto_increment; -- wymagany do dzialania aplikacji
alter table transport_type modify idtransport_type int(11) auto_increment; -- wymagany do dzialania aplikacji
alter table route drop foreign key fk_route_transport_type1; -- tymczasowe
alter table transport_type modify idtransport_type int(11) auto_increment; -- wymagany do dzilania aplikacji
alter table route ADD CONSTRAINT fk_route_transport_type1 FOREIGN KEY (route_transport_type) REFERENCES transport_type (idtransport_type) ON DELETE NO ACTION ON UPDATE NO ACTION; -- przywrocenie klucza
alter table point_list modify idmiddle_points int(11) auto_increment; -- wymagane do dzialania aplikacji
