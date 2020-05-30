package example.jms;

public class PubSub {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Example of pub sub model");


        // Setup
        MessageBroker messageBroker = new MessageBroker();
        Publisher pub1 = new Publisher(1);
        Publisher pub2 = new Publisher(2);
        Publisher pub3 = new Publisher(3);
        Publisher pub4 = new Publisher(4);

        Subscriber sub1 = new Subscriber(1);
        Subscriber sub2 = new Subscriber(2);
        Subscriber sub3 = new Subscriber(3);
        Subscriber sub4 = new Subscriber(4);

        Topic work = messageBroker.getTopicList().get(0);
        Topic random = messageBroker.getTopicList().get(1);
        Topic music = messageBroker.getTopicList().get(2);
        Topic goals = messageBroker.getTopicList().get(3);

        // register clients

        pub1.register();
        pub2.register();
        pub3.register();
        pub4.register();

        sub1.register();
        sub2.register();
        sub3.register();


        // set subscriptions first
        sub1.subscribeToTopic(work); // subscribe to the first topic
        sub2.subscribeToTopic(music);

        // Add messages asynchronously
        // Proper locking mechanisms are required
        Thread pubThread1 = new Thread(() -> {
            Message message = new Message(1,"Work: Help wanted from colleague"); // create the message. Might be due to any event like db call or passing stream data
            pub1.setMessage(message);
            pub1.publishMessage(work); // to an arbitrary topic for now
            System.out.println("Completed thread " + Thread.currentThread().getName());
        });

        Thread pubThread2 = new Thread(() -> {
            Message message = new Message(2,"Work: The boss wants to talk to you"); // create the message. Might be due to any event like db call or passing stream data
            pub1.setMessage(message);
            pub1.publishMessage(work); // to an arbitrary topic for now
            System.out.println("Completed thread " + Thread.currentThread().getName());
        });

        Thread pubThread3 = new Thread(() -> {
            Message message = new Message(3,"John Mayer: Released new album"); // create the message. Might be due to any event like db call or passing stream data
            pub2.setMessage(message);
            pub2.publishMessage(music); // to an arbitrary topic for now
            System.out.println("Completed thread " + Thread.currentThread().getName());
        });


        pubThread1.start();
        pubThread2.start();
        pubThread3.start();

        pubThread1.join();
        pubThread2.join();
        pubThread3.join();

        // Once published, the subscribers that are subscribed to the topic are triggered
        Thread subThread1 = new Thread(() -> {
            messageBroker.notifySubscribers();
        });
        subThread1.start();
        subThread1.join();

        sub1.showSubscribedMessages();

        messageBroker.showAllMessages();

    }
}
