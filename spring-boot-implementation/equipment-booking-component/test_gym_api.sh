#!/bin/bash

BASE_URL="http://localhost:8083/api"

echo "Waiting for server to start..."
until curl -s $BASE_URL/equipment > /dev/null; do
    sleep 2
    echo "..."
done
echo "Server is up!"

echo -e "\n\n1. === ADD MACHINE ==="
# We allow ID 0 to let the repo auto-generate one, or we can specify one.
# Let's specify 101 for predictable testing. 
# Note: Since the repo checks for 0 for auto-gen, let's try auto-gen first.
# Wait, the Machine constructor makes ID 0 by default if empty.
# Let's send a machine with ID 0 to see it auto-generate.
curl -X POST "$BASE_URL/equipment" \
     -H "Content-Type: application/json" \
     -d '{
           "regId": 0,
           "name": "Treadmill 3000",
           "brand": "TechnoGym",
           "model": "X1",
           "maxWeightCapacity": 150.0,
           "machineWeight": 200.0,
           "type": "Cardio"
         }' | python3 -m json.tool

echo -e "\n\n2. === LIST MACHINES ==="
# Capture the output to extract the ID of the new machine for booking
RESPONSE=$(curl -s "$BASE_URL/equipment")
echo "$RESPONSE" | python3 -m json.tool

# Extract the first machine's ID using basic grep/cut/sed or jq if available.
# Since jq might not be there, let's assume valid JSON format and just parse crudely or use a fixed ID if we knew it.
# Actually, let's parse it with a quick python one-liner to be safe.
MACHINE_ID=$(echo "$RESPONSE" | python3 -c "import sys, json; data=json.load(sys.stdin); print(data[0]['regId']) if data else print('')")

if [ -z "$MACHINE_ID" ]; then
    echo "No machines found! Test failed."
    exit 1
fi
echo "Target Machine ID: $MACHINE_ID"

echo -e "\n\n3. === BOOK MACHINE ==="
# Booking member ID 123 (DataService mock will handle this)
curl -X POST "$BASE_URL/bookings/book?machineId=$MACHINE_ID&memberId=123"

echo -e "\n\n4. === VERIFY BOOKING (See 'bookings' array) ==="
curl -s "$BASE_URL/equipment/$MACHINE_ID" | python3 -m json.tool

echo -e "\n\n5. === CANCEL BOOKING ==="
curl -X POST "$BASE_URL/bookings/cancel?machineId=$MACHINE_ID&memberId=123"

echo -e "\n\n6. === FINAL CHECK ==="
curl -s "$BASE_URL/equipment/$MACHINE_ID" | python3 -m json.tool

