if [ "$1" != "" ]
then
	echo `curl -X GET -u "$1:kieserver1!" "http://localhost:8080/kie-server/services/rest/server/queries/tasks/instances/pot-owners" -H "accept: application/json"  --silent | jq -r '."task-summary" | length'`
else
	echo "0"
fi
