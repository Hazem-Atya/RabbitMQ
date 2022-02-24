
# Simple collaborative text editor with swing and rabbitMQ
## Description
A simple collborative text editor.  This text editors lets many windows write and edit the same content (the same idea as google docs)

## Technology
* Java 
* Swing
* [RabbitMQ](https://www.rabbitmq.com/): RabbitMQ is a famous message oriented middleware

## RabbitMQ architecture
![image](https://user-images.githubusercontent.com/53778545/155602408-2e989c35-f9c1-4295-b6d7-056085f27a41.png)    

## Implementation 
The basic idea of a collaborative text editor is distribution, however for testing the code locally, the class SendMain creates all the windows of the app.
The main class contains a number n which is the number of windows that would be created.
``` java
    int n = 4;
        for (int i =1 ;i<=n;i++)
        {
            String queueName="file"+i;
            sendWindows.add(i-1,new Window(queueName));
            sendWindows.get(i-1).afficher(queueName);
        }
```

Every window has a queue that it reads from it. This queue contains the modification done by other windows.    
The window reads those modification and updates its content.
When a window X does a modification in the text, the modification are send to the queues of other windows. 


## How to use 
Just include RabbitMQ jars. The necessary jars are available in the official [RabbitMQ website](https://www.rabbitmq.com/) or in the folder [/jars](https://github.com/Hazem-Atya/RabbitMQ/tree/main/jars)
