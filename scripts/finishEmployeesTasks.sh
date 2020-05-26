employees=("karen" "mary" "bill" "jim" "lisa" "anton" "john")
for employee in ${employees[@]};  do
	echo "Handling tasks for employee $employee"
	auth="$employee:kieserver1!"
	evaluation=$((5 + RANDOM % 5))
	taskId=`curl -X GET -u ''$auth'' "http://localhost:8080/kie-server/services/rest/server/queries/tasks/instances/owners?status=Reserved" -H "accept: application/json" --silent| jq -r '."task-summary"[0]."task-id"'`
	
	if [ "$taskId" != "null" ]
	then
		echo "	Task ID: $taskId"
		sleep 1

		echo "	Starting $taskId"
		curl -X PUT -u ''$auth'' "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/tasks/$taskId/states/started" --silent
		echo "	Completing $taskId"
		sleep 1

		curl -X PUT -u ''$auth'' -H 'Content-type: application/json' --data '{"performance": "'$evaluation'"}' "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/tasks/$taskId/states/completed" --silent
		echo "Done task process for $employee"
		sleep $((1 + RANDOM % 5))
	else
		echo "No pending tasks for $employee."
	fi
done;
