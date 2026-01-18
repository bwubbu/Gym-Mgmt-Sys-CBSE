#!/bin/bash

BASE_URL="http://localhost:8083/api"

echo "Waiting for server to start..."
until curl -s $BASE_URL/equipment > /dev/null; do
    sleep 2
    echo "..."
done
echo "Server is up!"

echo -e "\n\n1. === ADD MACHINE (for maintenance) ==="
# Add a unique machine
RESP=$(curl -s -X POST "$BASE_URL/equipment" \
     -H "Content-Type: application/json" \
     -d '{
           "regId": 0,
           "name": "Rowing Machine 500",
           "brand": "WaterRower",
           "model": "A1",
           "maxWeightCapacity": 120.0,
           "machineWeight": 30.0,
           "type": "Cardio"
         }')
echo "$RESP" | python3 -m json.tool

# Extract ID
MACHINE_ID=$(echo "$RESP" | python3 -c "import sys, json; print(json.load(sys.stdin)['regId'])")
echo "Created Machine ID: $MACHINE_ID"

echo -e "\n\n2. === SCHEDULE MAINTENANCE ==="
# Date format: We haven't improved Date deserialization yet, so we assume standard JSON if Jackson handles it 
# or use the custom Date object structure from Base Library.
# Base Library Date has {day, month, year}.
curl -X POST "$BASE_URL/maintenance" \
     -H "Content-Type: application/json" \
     -d "{
           \"machineId\": $MACHINE_ID,
           \"description\": \"Routine chain oiling\",
           \"status\": \"SCHEDULED\",
           \"startDate\": { \"day\": 20, \"month\": 10, \"year\": 2026 },
           \"endDate\": { \"day\": 21, \"month\": 10, \"year\": 2026 }
         }"

echo -e "\n\n3. === GET MAINTENANCE HISTORY ==="
curl -s "$BASE_URL/maintenance/machine/$MACHINE_ID" | python3 -m json.tool

