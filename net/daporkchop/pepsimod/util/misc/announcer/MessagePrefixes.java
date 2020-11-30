// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.announcer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.EnumMap;
import java.util.Map;

public class MessagePrefixes
{
    private static Map<TaskType, MessageMaker> messageMakers;
    
    public static String getMessage(final TaskType type, final Object... args) {
        return "> " + MessagePrefixes.messageMakers.get(type).getMessage(args);
    }
    
    static {
        (MessagePrefixes.messageMakers = new EnumMap<TaskType, MessageMaker>(TaskType.class)).put(TaskType.JOIN, new MessageMaker(new String[] { "Welcome, %1$s", "Greetings, %1$s", "Hi %1$s!", "%1$s joined the game", "Hey there, %1$s" }));
        MessagePrefixes.messageMakers.put(TaskType.LEAVE, new MessageMaker(new String[] { "Bye, %1$s!", "See ya later, %1$s", "%1$s left the game" }));
        MessagePrefixes.messageMakers.put(TaskType.BREAK, new MessageMaker(new String[] { "I just mined %2$d %1$s!", "I just broke %2$d %1$s!" }));
        MessagePrefixes.messageMakers.put(TaskType.PLACE, new MessageMaker(new String[] { "I just placed %2$d %1$s!" }));
        MessagePrefixes.messageMakers.put(TaskType.EAT, new MessageMaker(new String[] { "I just ate %2$d %1$s!" }));
        MessagePrefixes.messageMakers.put(TaskType.WALK, new MessageMaker(new String[] { "I just walked %1$.2f meters!", "I just walked %1$.2f blocks!" }));
    }
    
    private static class MessageMaker
    {
        public String[] messages;
        
        public MessageMaker(final String[] messages) {
            this.messages = messages;
        }
        
        public String getMessage(final Object... args) {
            return String.format(this.messages[ThreadLocalRandom.current().nextInt(this.messages.length)], args);
        }
    }
}
