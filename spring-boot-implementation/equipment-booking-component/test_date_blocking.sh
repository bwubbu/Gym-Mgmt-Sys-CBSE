#!/bin/bash
BASE_URL="http://localhost:8083/api"

echo "Waiting for server..."
sleep 5

# 1. Add Machine
echo "Adding Machine..."
RESP=$(curl -s -X POST "$BASE_URL/equipment" -H "Content-Type: application/json" -d '{ "regId": 0, "name": "DateTestMach", "brand": "X", "model": "Y", "maxWeightCapacity": 100, "machineWeight": 50, "type": "Cardio" }')
echo "Machine Response: $RESP"
ID=$(echo "$RESP" | python3 -c "import sys, json; print(json.load(sys.stdin)['regId'])")
echo "Machine ID: $ID"

# 2. Schedule Maintenance (Jan 18 - Jan 20, 2026) -> COVERS TODAY (Jan 19)
echo "Scheduling Maintenance..."
curl -X POST "$BASE_URL/maintenance" -H "Content-Type: application/json" -d "{
  \"machineId\": $ID,
  \"description\": \"Fixing stuff\",
  \"status\": \"SCHEDULED\",
  \"startDate\": { \"day\": 18, \"month\": 1, \"year\": 2026 },
  \"endDate\": { \"day\": 20, \"month\": 1, \"year\": 2026 }
}"

# 3. Try to Book (Should Fail)
echo -e "\nTry to Book ($ID)..."
BOOK_RESP=$(curl -s -X POST "$BASE_URL/bookings/book?machineId=$ID&memberId=1")
echo "Booking Response: $BOOK_RESP"

if [[ "$BOOK_RESP" == *"under maintenance"* ]]; then
  echo "SUCCESS: Booking blocked as expected."
else
  echo "FAILURE: Booking was not blocked."
fi
