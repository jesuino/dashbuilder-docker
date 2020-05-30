Evaluation Dashboard Scripts
--

Scripts to generate Evaluations. Preferable run this with demos from this repository.

### Requirements:

* Kie Server running on `localhost:8080`
* jq utilty:

```
sudo dnf install jq
```
or
```
brew install jq
```

### How to run

`main.js` is the main script. It starts  process instances and complete all the tasks spawned by the process instances.

Each script can run independently if you want so:

* startEvaluations.sh: Start process instances
* finishEmployeesTasks.sh: Finishes the first tasks assigned to employees
* finishHRTasks.sh: Finish tasks assigned to HRs
* finishPMTasks.sh: Finish tasks assigned to PMs
* userPotentialOwner.sh: Utility to check tasks for a group
