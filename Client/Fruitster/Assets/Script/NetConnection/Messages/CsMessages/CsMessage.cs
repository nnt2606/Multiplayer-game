using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public abstract class CsMessage : Message
    {
        protected bool broadCast = false;
    }
}