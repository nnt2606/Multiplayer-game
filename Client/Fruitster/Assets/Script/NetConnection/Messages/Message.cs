using System;
using System.Net;
using Script.Utils;
using UnityEngine.Serialization;

namespace Script.Messages
{
    [Serializable]
    public abstract class Message
    {
        public long generatedTime;
        
        protected Message()
        {
            generatedTime = TimeUtils.CurrentTimeMillis();
        }

        public String GetMessageType()
        {
            return GetType().Name;
        }
    }
}