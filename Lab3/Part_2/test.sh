#!/bin/bash

curl -X POST -H "Content-Type: application/json" -d '{"name": "test", "value": 123}' http://localhost:5000/add_data

curl http://localhost:5000/get_data
