using System;
using System.Net;

namespace Script.Messages.CsMessages.GamePlay
{
    [Serializable]
    public class CsGameEnd : CsMessage
    {
        public HttpStatusCode statusCode;

        public CsGameEnd(HttpStatusCode statusCode)
        {
            this.statusCode = statusCode;
        }
    }
}