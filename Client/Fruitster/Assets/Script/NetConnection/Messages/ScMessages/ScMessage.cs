using System;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public abstract class ScMessage : Message
    {
        public abstract void OnMessage(Session session);
    }
}