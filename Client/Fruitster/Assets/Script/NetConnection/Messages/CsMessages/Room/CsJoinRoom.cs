using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsJoinRoom : CsMessage
    {
        public string roomID;
        public string password;

        public CsJoinRoom(string roomId, string password)
        {
            this.roomID = roomId;
            this.password = password;
        }
    }
}