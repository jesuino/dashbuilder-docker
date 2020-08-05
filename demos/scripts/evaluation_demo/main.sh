# Run all scripts together for a full demo
# Start Processes
./startEvaluations.sh &

sleep 5

# Start finishing Employee tasks
./finishEmployeesTasks.sh  &

sleep 5

# Finish PM and HR tasks
./finishPMTasks.sh  &

sleep 3

./finishHRTasks.sh  &
