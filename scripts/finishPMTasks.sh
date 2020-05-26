PMs=("ruth" "james")

pm=${PMs[0]};
total=`./userPotentialOwner.sh $pm`
if [ "$total" == "" ] 
then
	total="0"
fi
echo "Found $total PM tasks to Finish"
while [ "$total" -gt "0" ] 
do
	len=${#PMs[@]}
	i=$((RANDOM % len))
	pm=${PMs[i]}
	auth="$pm:kieserver1!"
	echo "Selected $pm to complete PM task"
	evaluation=$((5 + RANDOM % 5))

	taskId=`curl -X GET -u ''$auth'' "http://localhost:8080/kie-server/services/rest/server/queries/tasks/instances/pot-owners" -H "accept: application/json" --silent | jq -r '."task-summary"[0]."task-id"'`
	echo "	Task ID: $taskId"
	sleep 1


	echo "	Claiming $taskId"
	curl -X PUT -u ''$auth'' "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/tasks/$taskId/states/claimed" --silent
	sleep 1

	echo "	Starting $taskId"
	curl -X PUT -u ''$auth'' "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/tasks/$taskId/states/started" --silent
	sleep 1

	echo "	Completing $taskId"
	curl -X PUT -u ''$auth'' -H 'Content-type: application/json' --data '{"performance": "'$evaluation'"}' "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/tasks/$taskId/states/completed" --silent

	echo "Done task process for $pm"
	sleep $((1 + RANDOM % 5))
	total=`./userPotentialOwner.sh $pm`
	echo "Remaining $total PM tasks"
done
