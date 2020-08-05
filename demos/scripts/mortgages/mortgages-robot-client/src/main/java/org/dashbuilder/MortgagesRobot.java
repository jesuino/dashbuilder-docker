package org.dashbuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.myspace.mortgage_app.Applicant;
import com.myspace.mortgage_app.Application;
import com.myspace.mortgage_app.Property;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.kie.server.client.KieServicesFactory.newKieServicesClient;
import static org.kie.server.client.KieServicesFactory.newRestConfiguration;

public class MortgagesRobot {

    // Total process to start
    private static final int TOTAL_PROCESSES = 10;

    // Controls inner mortgage loop
    private static final int INLIMIT_CHANCES_STEP = 10;
    private static final int INITIAL_INLIMIT_CHANCES = 20;

    // If not increase downpayment then processes exits
    private static final int CHANCES_INCREASE_DOWNPYMT = 80;

    private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USER = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    private static final String CONTAINER = "mortgage";
    private static final String PROCESSID = "Mortgage_Process.MortgageApprovalProcess";
    private static final Random RANDOM = new Random();
    private static final int MAX_TIME_BETWEEN_ITERATIONS = 1000;

    private static final ProcessServicesClient processClient;
    private static final UserTaskServicesClient tasksClient;

    static {
        final KieServicesConfiguration conf = newRestConfiguration(URL, USER, PASSWORD);
        Set<Class<?>> extraClasses = new HashSet<>();
        extraClasses.add(Application.class);
        extraClasses.add(Applicant.class);
        extraClasses.add(Property.class);

        conf.addExtraClasses(extraClasses);
        conf.setMarshallingFormat(MarshallingFormat.JSON);

        final KieServicesClient client = newKieServicesClient(conf);
        processClient = client.getServicesClient(ProcessServicesClient.class);
        tasksClient = client.getServicesClient(UserTaskServicesClient.class);
    }

    public static void main(String[] args) {
        int i = TOTAL_PROCESSES;
        while (i-- > 0) {
            new Thread(() -> {
                try {
                    randomMortgage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        };

    }

    private static void randomMortgage() throws InterruptedException {
        long started = System.currentTimeMillis();
        boolean goodApplication = RANDOM.nextBoolean();

        Long piid = startMortage(application(goodApplication));
        System.out.println("Started: " + piid);

        while (!goodApplication) {
            System.out.println("Validation error for " + piid + ". Waiting for new application");
            goodApplication = RANDOM.nextBoolean();
            tasksComplete(piid, singletonMap("application", application(goodApplication)), 1000);
            iteractionSleep();
        }

        System.out.println("Validation sucessful for " + piid);

        int chancesToInlimit = INITIAL_INLIMIT_CHANCES;
        boolean completedTasks = false;

        while (!completedTasks) {
            // changes of pass inlimit increases after each interaction
            final boolean inlimit = RANDOM.nextInt(100) <= chancesToInlimit;
            tasksComplete(piid, singletonMap("inlimit", inlimit));


            if (!inlimit) {
                System.out.println("Process not inlimit: " + piid + ". Chances to inlimit: " + chancesToInlimit);
                // Rejects 80% of all requests
                final boolean incdownpayment = RANDOM.nextInt(100) <= CHANCES_INCREASE_DOWNPYMT;
                tasksComplete(piid, singletonMap("incdownpayment", incdownpayment));

                if (!incdownpayment) {
                    System.out.println("Mortage Rejected: " + piid + ". Time: " + secondsPassed(started));
                    return;
                }
                chancesToInlimit += INLIMIT_CHANCES_STEP;
                iteractionSleep();
            } else {
                completedTasks = true;
            }
        }
        System.out.println("Process ");
        // complete is slower than usual
        tasksComplete(piid, emptyMap(), 2000);
        iteractionSleep();

        System.out.println("Process Finished: " + piid + ". Time: " + secondsPassed(started) );
    }

    private static long secondsPassed(long currentTime) {
        return (System.currentTimeMillis() - currentTime) / 1000;
    }

    private static Long startMortage(Application app) {
        return processClient.startProcess(CONTAINER,
                                          PROCESSID,
                                          singletonMap("application", app));
    }

    private static Application application(boolean good) {
        return good ? goodApplication() : badApplication();
    }

    private static Application badApplication() {
        Application application = goodApplication();
        application.setDownpayment(0);
        return application;

    }

    private static Application goodApplication() {
        Applicant applicant = new Applicant("John", 100000, "John's street", 123456, 200000);
        Property prop = new Property(5, "Property address", "Urban", 300000);
        Application app = new Application();

        app.setApplicant(applicant);
        app.setProperty(prop);

        app.setDownpayment(50000);
        app.setMortgageamount(250000);
        return app;
    }

    private static void tasksComplete(Long piid, Map<String, Object> params) {
        tasksComplete(piid, params, MAX_TIME_BETWEEN_ITERATIONS);
    }

    private static void tasksComplete(Long piid, Map<String, Object> params, int min) {
        List<TaskSummary> tasks = tasksClient.findTasksByStatusByProcessInstanceId(piid,
                                                                                   emptyList(),
                                                                                   0,
                                                                                   100);
        tasks.forEach(t -> {
            try {
                iteractionSleep(min);
                tasksClient.claimTask(CONTAINER, t.getId(), USER);
                iteractionSleep(min);
                tasksClient.startTask(CONTAINER, t.getId(), USER);
                iteractionSleep(min);
                tasksClient.completeTask(CONTAINER, t.getId(), USER, params);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        });

    }

    private static void iteractionSleep() throws InterruptedException {
        iteractionSleep(0, MAX_TIME_BETWEEN_ITERATIONS);
    }

    private static void iteractionSleep(int min) throws InterruptedException {
        iteractionSleep(min, MAX_TIME_BETWEEN_ITERATIONS);
    }
    
    private static void iteractionSleep(int min, int maxTime) throws InterruptedException {
        Thread.sleep(min + RANDOM.nextInt(maxTime));
    }

}