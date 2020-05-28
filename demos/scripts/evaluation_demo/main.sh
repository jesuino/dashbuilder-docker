# Run all scripts together for a full demo
# Start Processes
./startEvaluations.sh &

sleep 15

# Start finishing Employee tasks
./finishEmployeesTasks.sh  &

sleep 10

# Finish PM and HR tasks
./finishPMTasks.sh  &
./finishHRTasks.sh  &