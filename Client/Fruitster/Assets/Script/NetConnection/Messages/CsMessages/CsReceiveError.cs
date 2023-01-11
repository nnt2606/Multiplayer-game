using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsReceiveError : CsMessage
    {
        public String message;

        public CsReceiveError(string message)
        {
            this.message = message;
        }
    }
}