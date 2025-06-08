# odevo-axon
Home assignment application

# Persistence?
Application is only for demo purpose and does not connect to a persistent store

# How to run test?
If you are an intellij user, you can check the src/main/resources folder for the endpoint-test.http file and execute the prepared http calls

If not, you can use Curl:

1. **Create a Work Order**
``` bash
   curl -X POST http://localhost:8080/workorders \
        -H "Content-Type: application/json" \
        -d '{"instruction": "The work to be done!"}'
```

2. **Retrieve a Specific Work Order**
   To fetch the details of a work order by its ID (woId):
``` bash
   curl -X GET http://localhost:8080/workorders/{{woId}} \
        -H "Content-Type: application/json"
```

3. **Assign a Person to a Work Order**
   To assign a specific person to an existing work order, use:
``` bash
   curl -X PUT http://localhost:8080/workorders/{{woId}}/assignee \
        -H "Content-Type: application/json" \
        -d '{"personId": "b921f1dd-3cbc-0495-fdab-8cd14d33f0aa"}'
```

4. **Execute a Work Order**
   This command marks the specified work order as executed:
``` bash
   curl -X PUT http://localhost:8080/workorders/{{woId}}/execution \
        -H "Content-Type: application/json" \
        -d '{}'
```
