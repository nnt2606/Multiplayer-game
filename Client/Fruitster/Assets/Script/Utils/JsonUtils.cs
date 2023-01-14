using System;
using System.Collections.Generic;
using System.Linq;
using Newtonsoft.Json;
using Script.Messages;
using Script.Messages.ScMessages;
using UnityEngine;

namespace Script.Utils
{
    public static class JsonUtils
    {
        public static string MessageToJson(Message message)
        {
            var json = JsonConvert.SerializeObject(message);
            return message.GetMessageType() + json;
        }

        public static IEnumerable<ScMessage> JsonToMessages(string json)
        {
            
            foreach (var messageString in DivideMessage(json))
            {
                var messageTypeLength = messageString.IndexOf('{');
                var messageType = AppDomain.CurrentDomain.GetAssemblies().Reverse().SelectMany(t => t.GetTypes()).First(t =>
                    string.Equals(t.Name, messageString.Substring(0, messageTypeLength), StringComparison.Ordinal));
                // Debug.Log("Message type: " + messageString.Substring(0, messageTypeLength));
                // Debug.Log("Message data: " + messageString.Substring(messageTypeLength, json.Length - messageTypeLength));
                if (!messageType.IsSubclassOf(typeof(Message)))
                    continue;
                yield return (ScMessage) JsonConvert.DeserializeObject(
                    messageString.Substring(messageTypeLength, messageString.Length - messageTypeLength), messageType);

            }
        }

        private static List<string> DivideMessage(string concatedMessage)
        {
            var messages = new List<string>();
            var start = 0;
            var total = 0;
            for (var i = 0; i < concatedMessage.Length; i++)
            {
                if (concatedMessage[i] == '{')
                    total++;
                if (concatedMessage[i] == '}')
                {
                    total--;
                    if (total == 0)
                    {
                        messages.Add(concatedMessage.Substring(start, i + 1 - start));
                        start = i + 1;
                    }
                }
            }
            return messages;
        }
    }
}