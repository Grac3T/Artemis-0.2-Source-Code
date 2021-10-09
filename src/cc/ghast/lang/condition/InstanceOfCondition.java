/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.PacketUtil;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.check.CustomCheckLoadError;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ICondition;
import cc.ghast.lang.util.ReflectionUtil;
import java.io.PrintStream;
import java.lang.reflect.Field;

@Condition(name="InstanceOf", use={"instanceof"})
public class InstanceOfCondition
implements ICondition {
    @Override
    public boolean value(String value, PlayerData data, AbstractCustomCheck check, NMSObject packet) {
        value = value.split("\\(")[1].split("\\)")[0];
        String __packet__ = value.split("instanceof")[1].replace(" ", "");
        String packetName = value.split("instanceof")[0].replace(" ", "");
        if (packet == null) {
            System.out.println("Invalid packet reeeeeeee");
            return false;
        }
        if (packetName.contains(".") && __packet__.contains(".")) {
            String var = packetName.split("\\.")[1].replace(" ", "");
            String pack = __packet__.split("\\.")[0].replace(" ", "");
            String packVar = __packet__.split("\\.")[1].replace(" ", "");
            try {
                String name = PacketUtil.findPacketName(packet);
                if (!name.equalsIgnoreCase(pack)) {
                    return false;
                }
                Field field = packet.getClass().getField(var);
                Class<?> type = field.getType();
                if (type.isEnum()) {
                    Enum num = (Enum)ReflectionUtil.getEnumConstant(type, packVar);
                    return field.get(packet) == num;
                }
                if (ReflectionUtil.isNumber(type)) {
                    return packVar == field.get(packet).toString();
                }
                if (ReflectionUtil.isBoolean(type)) {
                    return packVar == field.get(packet).toString();
                }
                if (ReflectionUtil.isString(type)) {
                    return packVar == field.get(packet).toString();
                }
                System.out.println("Invalid reflections field");
                throw new CustomCheckLoadError();
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        String name = PacketUtil.findPacketName(packet);
        return name.equalsIgnoreCase(__packet__);
    }
}

