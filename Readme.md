# Getting Started

### Setting up locally
1. Open terminal and move to any directory of your choice
2. create a new directory with name `flow-charts-application`
    ```bash
    mkdir flow-charts-application
    cd flow-charts-application
    ``` 
3. Clone the application using the shared GitHub repository [link](https://github.com/nikhiltejag/flow-chart-service.git)
   ```bash
   git clone https://github.com/nikhiltejag/flow-chart-service.git
   ```
4. Build the application using maven (make sure you have the latest maven and java(version: 17 or latest) installed in your system)
   ```bash
   mvn clean install
   ```
5. It will create a jar file inside `./target` folder, execute below command to run the project using maven.
   ```bash
   mvn spring-boot:run
   ```
   Or else you can directly the jar using below command
   ```bash
   java -jar .\target\flowcharts-0.0.1-SNAPSHOT.jar
   ```
6. If you see below line in the application logs, then we have successfully running the application. Time taken to run may vary
   ```text
    c.demo.flowcharts.FlowChartsApplication  : Started FlowChartsApplication in 6.193 seconds
   ```
7. Hurray, our project setup is completed, let's delve into the features in the next section


### Checking Application status
1. After you complete the steps in above section, we can see application started in `localhost:8080`
2. You can change the port by adding below property in `application.yml` file
   ```yaml
   server:
    port : <port>
   ```
3. You can check application status using the health API `localhost:8080/health`
   ```bash
   curl --location 'http://localhost:8080/health'
   ```
   You should see below response, if not please re-do the setup process.
   ```json
   {
    "status": "Success",
    "message": "FlowChartsApplication is up and running..."
   }
   ```
   
### Using flow charts APIs
We have 4 APIs for managing flow charts in this application.
1. Create Flow Chart
2. Fetch flow chart using ID
3. Update flow chart
4. Delete flow chart

You can use below cURL requests in given order to test each API.
Or else you can import the attached [postman collection](Flow%20chart.postman_collection.json) and see examples or invoke the APIs.

### Create flow-chart
#### API: (POST `/flow-charts`)
1. To create a flow you need to provide name, list of nodes, list of edges.
2. Edge should be a list of length 2 `["node_1", "node_2"]`. Indicating a directed edge from `node_1` to `node_2`.
3. Use below cURL to create flow chart `chart-1`
   ```bash CreateChart
   curl --location 'http://localhost:8080/flow-charts' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "chart-1",
   "nodes": ["1", "2", "3"],
   "edges": [["1", "2"], ["2", "3"]]
   }'
   ```
4. It will create a flow chart, nodes, edges in the database and return an id in the response (id will change).
   ```json
   {
    "id": "43666f1f-f36f-463c-bbb2-9f8370c06a89",
    "name": "chart-1",
    "nodes": [
        "1",
        "2",
        "3"
    ],
    "edges": [
        [
            "1",
            "2"
        ],
        [
            "2",
            "3"
        ]
    ]
   }
   ```
### Fetch flow-chart
#### API: (GET `/flow-charts/{id}`)
1. Replace the `{id}` field with id we got in [Create Flow chart](#Create-Flow-chart) (if you use postman id will be auto updated through postman scripts).
2. use below cURL (after replacing id).
   ```bash
   curl --location --request GET 'http://localhost:8080/flow-charts/b149f771-efff-4a10-b659-ffaba8a34125' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "chart-1",
   "nodes": ["1", "2", "3"],
   "edges": [["1", "2"], ["2", "3"]]
   }'
   ```
   You would see response in below format
   ```json
      {
    "id": "43666f1f-f36f-463c-bbb2-9f8370c06a89",
    "name": "chart-1",
    "nodes": [
        "1",
        "2",
        "3"
    ],
    "edges": [
        [
            "2",
            "3"
        ],
        [
            "1",
            "2"
        ]
    ]
   }
   ```

### Update flow-chart (add nodes or edges)
#### API: (PATCH `/flow-charts/{id}`)
1. Replace the `{id}` field with id we got in [Create Flow chart](#Create-Flow-chart) (if you use postman id will be auto updated through postman scripts).
2. use below cURL 
   ```bash
   curl --location --globoff --request PATCH 'http://localhost:8080/flow-charts/{{chartId}}' \
   --data '{
       "nodes": ["5"],
       "edges": [["2", "5"]]
   }'
   ```
   > You can also update name of flowchart using `name`.
3. It will add a new node `5` and edge `2 -> 5` and you would see updated flow chart in the response
   ```json
   {
       "id": "43666f1f-f36f-463c-bbb2-9f8370c06a89",
       "name": "chart-1",
       "nodes": [
           "1",
           "2",
           "3",
           "5"
       ],
       "edges": [
           [
               "2",
               "3"
           ],
           [
               "2",
               "5"
           ],
           [
               "1",
               "2"
           ]
       ]
   }
   ```

### Update flow-chart (remove nodes or edges)
#### API: (PATCH `/flow-charts/{id}`)
1. To remove nodes or edges using same patch API, we need to use a parameter `operation=remove`.
2. In above API we didn't send any parameter because if we do not mention any the default operation is `add`.
3. Replace the `{id}` field with id we got in [Create Flow chart](#Create-Flow-chart) (if you use postman id will be auto updated through postman scripts).
4. Use below cURL
   ```bash
   curl --location --globoff --request PATCH 'http://localhost:8080/flow-charts/{{chartId}}?operation=remove' \
   --data '{
       
       "nodes": ["5"]
       
   }'
   ```
   > Whenever we are removing a node, we will also remove all the edges related to that node.
5. Expected response
```json
{
    "id": "43666f1f-f36f-463c-bbb2-9f8370c06a89",
    "name": "chart-1",
    "nodes": [
        "1",
        "2",
        "3"
    ],
    "edges": [
        [
            "2",
            "3"
        ],
        [
            "1",
            "2"
        ]
    ]
}
```

### Delete flow-chart
#### API: (DELETE `/flow-charts/{id}`)
1. Replace the `{id}` field with id we got in [Create Flow chart](#Create-Flow-chart) (if you use postman id will be auto updated through postman scripts).
2. Use below cURL
   ```bash
   curl --location --request DELETE 'http://localhost:8080/flow-charts/b149f771-efff-4a10-b659-ffaba8a34125'
   ```
3. Expected status code: 204 No Content