using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsGetRoom : CsMessage
    {
        public string roomID;

        public CsGetRoom(string roomId)
        {
            this.roomID = roomId;
        }
    }
}