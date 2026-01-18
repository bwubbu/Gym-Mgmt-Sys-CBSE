#!/bin/bash
BASE_URL="http://localhost:8083/api"

echo "Waiting for server..."
sleep 5

# 1. Add Machine
echo "Adding Machine for stats..."
RESP=$(curl -s -X POST "$BASE_URL/equipment" -H "Content-Type: application/json" -d '{ "regId": 0, "name": "StatsRun", "brand": "X", "model": "Y", "maxWeightCapacity": 100, "machineWeight": 50, "type": "Cardio" }')
ID=$(echo "$RESP" | python3 -c "import sys, json; print(json.load(sys.stdin)['regId'])")

# 2. Book 1 Slot
echo "Booking Machine $ID..."
curl -s -X POST "$BASE_URL/bookings/book?machineId=$ID&memberId=1" > /dev/null

# 3. Get Stats
echo -e "\n=== USAGE STATS ==="
STATS=$(curl -s "$BASE_URL/equipment/stats")
echo "$STATS" | python3 -m json.tool

# Verify our machine
echo -e "\nVerifying specific machine utilization..."
echo "$STATS" | python3 -c "
import sys, json
data = json.load(sys.stdin)
for m in data:
    if m['machineId'] == $ID:
        print(f'Machine: {m['machineName']}, Booked: {m['bookedSlots']}, Util: {m['utilizationPercentage']}%')
"
