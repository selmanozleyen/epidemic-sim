package persons;

import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Mediator object
 */
public class People implements IPeople{
    private final ArrayList<IPerson> availablePersons = new ArrayList<>();
    private final ArrayList<Conversation> talkingPeople = new ArrayList<>();
    private final ArrayList<IPerson> grave = new ArrayList<>();
    private final double spreadFactor;
    private final double mortalityRate;
    private final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public double getMortalityRate() {
        return mortalityRate;
    }

    public People(double spreadFactor, double mortalityRate) {
        this.spreadFactor = spreadFactor;
        this.mortalityRate = mortalityRate;
    }

    @Override
    public double getSpreadFactor(){
        return spreadFactor;
    }

    @Override
    public void addPersonList(List<IPerson> people){
        availablePersons.addAll(people);
    }
    @Override
    public void update(ITown t, GraphicsContext context)
    {
        updateTalks();
        updateGrave();
        makeMatches();

        for (IPerson p : availablePersons){
            p.update(t,context);
        }
        for(Conversation c: talkingPeople){
            c.update(t,context);
        }
    }

    @Override
    public List<IPerson> getAvailablePersons() {
        return availablePersons;
    }

    private void updateGrave(){
        List<IPerson> toRemove =
                availablePersons.stream().
                        filter(p -> p.getHealthState().isDead()).collect(Collectors.toList());
        grave.addAll(toRemove);
        availablePersons.removeAll(toRemove);
    }
    private void updateTalks(){
        ArrayList<Conversation> toRemove = new ArrayList<>();
        talkingPeople.forEach((c) -> {
            if(c.isOver){
                availablePersons.add(c.a);
                availablePersons.add(c.b);
                toRemove.add(c);
            }
        });
        talkingPeople.removeAll(toRemove);
    }
    private void makeMatches(){
        HashSet<IPerson> includes = new HashSet<>();
        for(IPerson p1 : availablePersons){
            for(IPerson p2: availablePersons){
                if(!includes.contains(p1) && !includes.contains(p2) && p1.inSocialField(p2) && p1 != p2){
                    includes.add(p1);
                    includes.add(p2);
                    talkingPeople.add(new Conversation(p1,p2));
                }
            }
        }
        for(Conversation c: talkingPeople){
            availablePersons.remove(c.a);
            availablePersons.remove(c.b);
        }

    }
    private class  Conversation{
        IPerson a;
        IPerson b;
        double timeLeftToConversation;
        double commonSocialDistance;
        boolean isOver = false;
        Conversation(IPerson a, IPerson b){
            this.a = a; this.b = b;
            timeLeftToConversation = Math.max(
                    a.getHealthState().getComponent().getSocialTimeout(),
                    b.getHealthState().getComponent().getSocialTimeout()
            );
            commonSocialDistance = Math.min(
                    a.getHealthState().getComponent().getSocialDistance(),
                    b.getHealthState().getComponent().getSocialDistance()
            );
            if(a.getHealthState().isInfected() ^ b.getHealthState().isInfected()){
                double p = getSpreadFactor() * (1+timeLeftToConversation/10) * a.getHealthState().getComponent().getMaskCoefficient()
                        * b.getHealthState().getComponent().getMaskCoefficient() * (1-commonSocialDistance/10.0);
                p = Math.min(1,p);
                /*If Got Infected*/
                if (p > ThreadLocalRandom.current().nextDouble()){
                    if(!a.getHealthState().isInfected()){
                        LOG.log(Level.INFO,"Infected: "+a+" p: "+p);
                        a.getHealthState().setInfected(true);
                        a.getHealthState().setTimeToHospital(25);
                        a.getHealthState().setTimeToDie(100*(1-getMortalityRate()));
                    } else{
                        LOG.log(Level.INFO,"Infected: "+b+" p: "+p);
                        b.getHealthState().setInfected(true);
                        a.getHealthState().setTimeToHospital(25);
                        b.getHealthState().setTimeToDie(100*(1-getMortalityRate()));
                    }
                }
            }
            assert timeLeftToConversation < 0 || commonSocialDistance >= 0;
            timeLeftToConversation = timeLeftToConversation*60;
        }
        void update(ITown town, GraphicsContext gc){
            if(!isOver){
                if (timeLeftToConversation > 0 &&
                    !a.getHealthState().isDead() &&
                    !b.getHealthState().isDead()
                ){
                    timeLeftToConversation--;
                    if (a.getPhysicalState().isEnabled()){
                        a.getPhysicalState().setEnabled(false);
                    }
                    if (b.getPhysicalState().isEnabled()){
                        b.getPhysicalState().setEnabled(false);
                    }
                }
                else{
                    isOver = true;
                    if (!a.getHealthState().isDead()){
                        a.getPhysicalState().setEnabled(true);
                    }
                    if(!b.getHealthState().isDead()){
                        b.getPhysicalState().setEnabled(true);
                    }
                }
            }
            a.update(town,gc);
            b.update(town,gc);
        }
    }
}
