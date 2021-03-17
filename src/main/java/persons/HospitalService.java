package persons;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HospitalService {
    final private static int threadCount = 2;
    ArrayBlockingQueue<IPerson> recovering;
    TransferQueue<IPerson> recovered = new LinkedTransferQueue<>();
    Logger log;
    ExecutorService exec;
    Button pauseBtn = null;
    HospitalService(int hospitalCapacity){
        exec = Executors.newFixedThreadPool(
                threadCount,
                (r) -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
        log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        // get a person
        this.recovering = new ArrayBlockingQueue<>(hospitalCapacity);
    }
    public  void setPauseBtn(Button pauseBtn){
        this.pauseBtn = pauseBtn;
    }

    void addPatients(final List<IPerson> patients) {
        VentilatorService task = new VentilatorService(patients,recovered,recovering);
        if (pauseBtn != null){
            pauseBtn.addEventHandler(ActionEvent.ACTION, (e)-> task.toggle());
        }
        recovering.addAll(patients);
        exec.submit(task);
    }
    int remainingCapacity(){
        return recovering.remainingCapacity();
    }
    public void update(Town town, List<IPerson> availablePeople) {
        if (availablePeople.size() > 0){
            ArrayList<IPerson> patients = new ArrayList<>();
            Iterator<IPerson> it = availablePeople.iterator();
            IPerson p;
            while(it.hasNext() && patients.size() <= remainingCapacity()){
                p = it.next();
                if(!p.getHealthState().isDead()
                        && p.getHealthState().isInfected()
                        && p.getHealthState().getTimeToHospital()<=0)
                {
                    patients.add(p);
                }
            }
            availablePeople.removeAll(patients);

            /* add patients to recovering atomically*/
            if (patients.size() > 0) {
                addPatients(patients);
            }
            patients = null;
            /*ensuring patients structure is not modified from now on */
        }
        /* move from recovered to availablePeople atomically
        *  and set infected false
        * */
        recovered.removeIf(p -> {
                    p.getHealthState().setInfected(false);
                    return availablePeople.add(p);
        });
    }
    private static class VentilatorService extends Task<Void>{
        private final Queue<IPerson> recovering;
        private final List<IPerson> patients;
        private final Queue<IPerson> recovered;
        private final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        private boolean paused=false;
        VentilatorService(final List<IPerson> patients,
                          Queue<IPerson> recovered,
                          Queue<IPerson> recovering){
            this.patients = patients;
            this.recovered = recovered;
            this.recovering = recovering;
        }
        @Override
        public Void call() {
            LOG.log(Level.INFO,"Curing: "+patients);
            // this is equal to 10 seconds
            for (int i = 0; i < 625; i++) {
                waitForResume();
                // sleep to simulate recovery
                try {
                        Thread.sleep(16);
                } catch (InterruptedException e) {
                    // pause button
                    //e.printStackTrace();
                }
            }
            LOG.log(Level.INFO,"Cured: "+patients);

            recovering.removeAll(patients);
            recovered.addAll(patients);
            return null;
        }

        synchronized void waitForResume(){
            while(paused){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private synchronized void toggle(){
            if(paused){
                resume();
            }else{
                pause();
            }
        }

        private synchronized void pause() {
            paused = true;
        }
        private synchronized void resume(){
            paused = false;
            notifyAll();
        }
    }
}
