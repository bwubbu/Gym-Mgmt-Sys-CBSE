#!/bin/bash

# Configuration
BASE_URL="http://localhost:8083/api"

# Function to check if server is running
check_server() {
    curl -s "$BASE_URL/equipment" > /dev/null
    return $?
}

# Start Server if not running
if ! check_server; then
    echo "Starting Equipment Booking Component..."
    mvn spring-boot:run &
    PID=$!
    echo "Waiting for server to start (PID: $PID)..."
    
    # Wait loop
    count=0
    until check_server; do
        sleep 2
        echo -n "."
        count=$((count+1))
        if [ $count -ge 30 ]; then
            echo "\nServer failed to start!"
            exit 1
        fi
    done
    echo "\nServer is up!"
fi

# Interactive Menu
while true; do
    echo -e "\n=== Machine Management Demo (Spring Boot) ==="
    echo "1. View Inventory"
    echo "2. Add Machine"
    echo "3. Book Machine Slot"
    echo "4. Schedule Maintenance"
    echo "5. View Usage Statistics"
    echo "6. Exit"
    echo -n "Enter choice: "
    read choice

    case $choice in
        1)
            echo "Fetching inventory..."
            curl -s "$BASE_URL/equipment" | python3 -m json.tool
            ;;
        2)
            echo "Enter Machine Details:"
            echo -n "Name: "
            read name
            echo -n "Type: "
            read type
            echo -n "Brand: "
            read brand
            echo -n "Model: "
            read model
            
            # Construct JSON
            JSON="{\"regId\":0, \"name\":\"$name\", \"type\":\"$type\", \"brand\":\"$brand\", \"model\":\"$model\", \"price\":1000.0, \"machineWeight\":100.0}"
            
            echo "Adding machine..."
            curl -s -X POST "$BASE_URL/equipment" \
                 -H "Content-Type: application/json" \
                 -d "$JSON" | python3 -m json.tool
            ;;
        3)
            echo -n "Enter Machine ID: "
            read mid
            echo -n "Enter Member ID: "
            read memid
            
            echo "Booking slot..."
            curl -s -X POST "$BASE_URL/bookings/book?machineId=$mid&memberId=$memid"
            echo ""
            ;;
        4)
            echo -n "Enter Machine ID: "
            read mid
            echo "Enter Start Date:"
            echo -n "  Month (1-12): "
            read sm
            echo -n "  Day: "
            read sd
            echo -n "  Year: "
            read sy
            
             echo "Enter End Date:"
            echo -n "  Month (1-12): "
            read em
            echo -n "  Day: "
            read ed
            echo -n "  Year: "
            read ey
            
            echo -n "Description: "
            read desc
            
            JSON="{\"machineId\":$mid, \"startDate\":{\"month\":$sm,\"day\":$sd,\"year\":$sy}, \"endDate\":{\"month\":$em,\"day\":$ed,\"year\":$ey}, \"description\":\"$desc\", \"status\":\"SCHEDULED\"}"
            
            echo "Scheduling maintenance..."
             curl -s -X POST "$BASE_URL/maintenance" \
                 -H "Content-Type: application/json" \
                 -d "$JSON"
             echo ""
            ;;
        5)
            echo "Fetching statistics..."
            curl -s "$BASE_URL/equipment/stats" | python3 -m json.tool
            ;;
        6)
            echo "Exiting..."
            break
            ;;
        *)
            echo "Invalid choice."
            ;;
    esac
    echo ""
    echo "Press Enter to continue..."
    read dummy
done
