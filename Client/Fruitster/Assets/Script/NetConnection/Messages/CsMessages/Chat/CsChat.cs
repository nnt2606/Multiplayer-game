using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsChat: CsMessage
    {
        public string message;

        public CsChat( string message)
        {
            this.message = message;
        }
    }
}