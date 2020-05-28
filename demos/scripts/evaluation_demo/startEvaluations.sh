
# Check if a specific reason was set
reason="General Evaluation"
if [[ "$1" != "" ]]
then
        reason="$1"
fi

employees=("karen" "mary" "bill" "jim" "lisa" "anton" "john")

for employee in ${employees[@]};  do
	payload='{"employee" : "'$employee'", "reason" : "'$reason'"}'
	curl -X POST -u "$employee:kieserver1!" -H 'Content-type: application/json' --data "$payload" "http://localhost:8080/kie-server/services/rest/server/containers/evaluation/processes/evaluation/instances" --silent > /dev/null
	echo "Started Evaluation for User $employee"
	sleep 2
done
