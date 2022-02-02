
#### post characters
curl -i -X POST -H 'Content-Type: application/json' -d '{"name": "BatMan", "description": "avia mouse"}' http://localhost:8080/characters

#### put characters
curl -i -X PUT -H 'Content-Type: application/json' -d '{"id": 100001, "name": "NewSomeName", "description": "Super Hero E"}' http://localhost:8080/characters/100001