public class NotifyObserver extends Observer{

    public NotifyObserver(Subject subject){
       this.subject = subject;
       this.subject.attach(this);
    }
 
    @Override
    public void update() {
       System.out.println( "Notification: " + subject.getState()); 
    }
 }