/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ReflectionsUtil;

public class WrappedChatMessage
extends NMSObject {
    private static String type = NMSObject.Type.CHATMESSAGE;
    private String chatMessage;
    private Object[] objects;
    private static FieldAccessor<String> messageField = WrappedChatMessage.fetchField(type, String.class, 0);
    private static FieldAccessor<Object[]> objectsField = WrappedChatMessage.fetchField(type, Object[].class, 0);

    public WrappedChatMessage(String chatMessage, Object ... object) {
        this.chatMessage = chatMessage;
        this.objects = object;
    }

    public WrappedChatMessage(String chatMessage) {
        this(chatMessage, new Object[0]);
    }

    @Override
    public void setPacket(String packet, Object ... args) {
        Class<?> chatMsgClass = ReflectionsUtil.getClass(type);
        Object o = ReflectionsUtil.newInstance(chatMsgClass, packet, args);
        this.setObject(o);
    }

    public WrappedChatMessage(Object object) {
        super(object);
        this.chatMessage = this.fetch(messageField);
        this.objects = this.fetch(objectsField);
    }

    public String getChatMessage() {
        return this.chatMessage;
    }

    public Object[] getObjects() {
        return this.objects;
    }
}

