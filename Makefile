.PHONY: api all test

all:	api
	@docker stack deploy -c ./docker-compose.yaml person
api:
	@docker image build -t person_api:latest .

test:
	@watch -n 3 curl http://localhost:8080/api/persons
