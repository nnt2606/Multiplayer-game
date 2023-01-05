package utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class JsonUtils {
    private static Map<String, String> classMap = Map.ofEntries(
            entry("CsChat", "message.csmessage.chat.CsChat"),
            entry("CsInitSession", "message.csmessage.session.CsInitSession"),
            entry("CsLogin", "message.csmessage.usermanagement.CsLogin"),
            entry("CsRegister", "message.csmessage.usermanagement.CsRegister"),
            entry("CsChangeAvatar", "message.csmessage.usermanagement.CsChangeAvatar"),
            entry("CsCreateRoom", "message.csmessage.roommanagement.CsCreateRoom"),
            entry("CsJoinRoom", "message.csmessage.roommanagement.CsJoinRoom"),
            entry("CsLeaveRoom", "message.csmessage.roommanagement.CsLeaveRoom"),
            entry("CsGetRoom", "message.csmessage.roommanagement.CsGetRoom"),
            entry("CsGetRoomList", "message.csmessage.roommanagement.CsGetRoomList"),
            entry("CsGameStart", "message.csmessage.game.CsGameStart"),
            entry("CsCharacterState", "message.csmessage.game.CsCharacterState"),
            entry("CsUserObtainItem", "message.csmessage.game.CsUserObtainItem")
    );

    public static String messageToJson(Message message) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(message);
            return message.getClass().getSimpleName() + json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Message jsonToMessage(String json) {
        int messageTypeLength = json.indexOf('{');
        String simpleName = json.substring(0, messageTypeLength);
        try {
            Class messageType = Class.forName(classMap.get(simpleName));
            if (messageType == null || !Message.class.isAssignableFrom(messageType)) {
                return null;
            } else {
                ObjectMapper mapper = new ObjectMapper();
                return (Message) mapper.readValue(json.substring(messageTypeLength, json.length()), messageType);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //ScGetRoom{
    //  "generatedTime" : 1658463643565,
    //  "statusCode" : 200,
    //  "roomName" : "",
    //  "roomID" : "d92005ff-763f-4bfb-8e3e-5fd08a2dca5f",
    //  "roomMaster" : "99bf6af6-f871-4dec-86e0-b27947b0b67b",
    //  "userList" : [ {
    //    "userID" : "99bf6af6-f871-4dec-86e0-b27947b0b67b",
    //    "userName" : "a?"
    //  } ]
    //}ScGetRoom{
    //  "generatedTime" : 1658463643565,
    //  "statusCode" : 200,
    //  "roomName" : "",
    //  "roomID" : "d92005ff-763f-4bfb-8e3e-5fd08a2dca5f",
    //  "roomMaster" : "99bf6af6-f871-4dec-86e0-b27947b0b67b",
    //  "userList" : [ {
    //    "userID" : "99bf6af6-f871-4dec-86e0-b27947b0b67b",
    //    "userName" : "a?"
    //  } ]
    //}ScGetRoom{
    //  "generatedTime" : 1658463643565,
    //  "statusCode" : 200,
    //  "roomName" : "",
    //  "roomID" : "d92005ff-763f-4bfb-8e3e-5fd08a2dca5f",
    //  "roomMaster" : "99bf6af6-f871-4dec-86e0-b27947b0b67b",
    //  "userList" : [ {

    public static List<String> divideMessage(String concatedMessage) {
        List<String> messages = new ArrayList<String>();
        int start = 0;
        int total = 0;
        for (int i = 0; i < concatedMessage.length(); i++) {
            if (concatedMessage.charAt(i) == '{') {
                total++;
            }
            if (concatedMessage.charAt(i) == '}') {
                total--;
                if (total == 0) {
                    messages.add(concatedMessage.substring(start, i + 1));
                    start = i + 1;
                }
            }
        }
        return messages;
    }
//    ScSpawnItem{
//    "generatedTime" : 1658463645711,
//    "itemID" : "6cc26725-e3ca-4db7-9842-c99c70e115b1",
//    "itemName" : "Apple",
//    "positionX" : 12,
//    "positionY" : 18
//    }    ScSpawnItem{
//        "generatedTime" : 1658463645711,
//        "itemID" : "6cc26725-e3ca-4db7-9842-c99c70e115b1",
//        "itemName" : "Apple",
//        "positionX" : 12,
//        "positionY" : 18
//        }    ScSpawnItem{
//        "generatedTime" : 1658463645711,
//        "itemID" : "6cc26725-e3ca-4db7-9842-c99c70e115b1",
//        "itemName" : "Apple",
//        "positionX" : 12,
//        "positionY" : 18
//        }
    public static void main(String args[]) {
        List<String> msgs = divideMessage("ScUserObtainItem{\n" +
                "  \"generatedTime\" : 1659608875204,\n" +
                "  \"statusCode\" : 200,\n" +
                "  \"userID\" : \"a3b40f2c-5e63-451e-8521-e1b263a65f89\",\n" +
                "  \"itemID\" : \"24b1036d-35cf-4cfe-b2f0-3f6754782292\",\n" +
                "  \"addPoint\" : 1\n" +
                "}ScSpawnItem{\n" +
                "  \"generatedTime\" : 1659608875204,\n" +
                "  \"itemID\" : \"75d80459-5f75-4987-8f94-52d575458b5a\",\n" +
                "  \"itemName\" : \"Apple\",\n" +
                "  \"positionX\" : 2,\n" +
                "  \"positionY\" : 9\n" +
                "}");
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }
}
