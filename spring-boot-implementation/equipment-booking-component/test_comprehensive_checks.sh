#!/bin/bash
BASE_URL="http://localhost:8083/api"

echo "Waiting for server..."
sleep 5

echo -e "\n=== SETUP ==="
# Add Mach1
RESP1=$(curl -s -X POST "$BASE_URL/equipment" -H "Content-Type: application/json" -d '{ "regId": 0, "name": "MachBlock", "brand": "X", "model": "Y", "maxWeightCapacity": 100, "machineWeight": 50, "type": "Cardio" }')
ID1=$(echo "$RESP1" | python3 -c "import sys, json; print(json.load(sys.stdin)['regId'])")
echo "Added Block Machine: $ID1"

# Add Mach2
RESP2=$(curl -s -X POST "$BASE_URL/equipment" -H "Content-Type: application/json" -d '{ "regId": 0, "name": "MachAllow", "brand": "X", "model": "Y", "maxWeightCapacity": 100, "machineWeight": 50, "type": "Cardio" }')
ID2=$(echo "$RESP2" | python3 -c "import sys, json; print(json.load(sys.stdin)['regId'])")
echo "Added Allow Machine: $ID2"

echo -e "\n=== SCHEDULE MAINTENANCE ==="
# Mach1: COVERS TODAY (Jan 19)
curl -s -X POST "$BASE_URL/maintenance" -H "Content-Type: application/json" -d "{
  \"machineId\": $ID1, \"status\": \"SCHEDULED\", \"description\": \"Block\",
  \"startDate\": { \"day\": 18, \"month\": 1, \"year\": 2026 },
  \"endDate\": { \"day\": 20, \"month\": 1, \"year\": 2026 }
}" > /dev/null

# Mach2: FUTURE (Feb 1)
curl -s -X POST "$BASE_URL/maintenance" -H "Content-Type: application/json" -d "{
  \"machineId\": $ID2, \"status\": \"SCHEDULED\", \"description\": \"Allow\",
  \"startDate\": { \"day\": 1, \"month\": 2, \"year\": 2026 },
  \"endDate\": { \"day\": 10, \"month\": 2, \"year\": 2026 }
}" > /dev/null

echo -e "\n=== TESTS ==="

# Test 1: Book Mach1 (Expect BLOCK)
RESP_FAIL=$(curl -s -X POST "$BASE_URL/bookings/book?machineId=$ID1&memberId=1")
echo "Booking Mach1 Response: $RESP_FAIL"
if [[ "$RESP_FAIL" == *"under maintenance"* ]]; then
  echo "PASS: Mach1 blocked."
else
  echo "FAIL: Mach1 NOT blocked."
fi

# Test 2: Book Mach2 (Expect SUCCESS)
RESP_SUCC=$(curl -s -X POST "$BASE_URL/bookings/book?machineId=$ID2&memberId=1")
echo "Booking Mach2 Response: $RESP_SUCC"
if [[ "$RESP_SUCC" == "booked" ]]; then
  echo "PASS: Mach2 allowed."
else
  echo "FAIL: Mach2 blocked/error ($RESP_SUCC)."
fi
